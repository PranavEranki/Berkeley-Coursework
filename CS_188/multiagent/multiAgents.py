# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from argparse import Action

from util import manhattanDistance
from game import Directions
import random, util

from game import Agent
from pacman import GameState

class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """


    def getAction(self, gameState: GameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState: GameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        ghostDistance_Scared = [(ghostState.getPosition(), ghostState.scaredTimer) for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        """
        Our evaluation will be based on:
        - How much food is left (less food is )
        - Are we getting closer to a ghost from the current to action game state, and is the distance < that ghost's scared time
        """

        evaluation = 0
        for ghostPos, timer in ghostDistance_Scared:
            if successorGameState.getPacmanPosition() == ghostPos:
                evaluation -= 1000
            if timer:
                if manhattanDistance(newPos, ghostPos) <= timer:
                    evaluation += (timer - manhattanDistance(newPos, ghostPos)) # rewarding being closer:
                else:
                    evaluation -= 2
            else:
                evaluation -= 1/2 * max(4 - manhattanDistance(newPos, ghostPos), 0) # rewarding the ghosts being farther!
        if newFood.asList():
            closest_new = min(manhattanDistance(newPos, point) for point in newFood.asList()) # reward being closer to some food object
            closest_old = min(manhattanDistance(newPos, point) for point in currentGameState.getFood().asList())
            evaluation += 3/closest_new if closest_new >= closest_old else 3/closest_old

        return successorGameState.getScore() + evaluation

def scoreEvaluationFunction(currentGameState: GameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def getAction(self, gameState: GameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        
        def recursive_max(gs, d, agent):
            p = gs.getLegalActions(agent)
            # if Directions.STOP in p:
            #     p.remove(Directions.STOP)
            if (not len(p) or d == 0):
                return (self.evaluationFunction(gs), 'STOP')
            if (gs.isWin() or gs.isLose()):
                return (self.evaluationFunction(gs), agent)
            successors = [(gs.generateSuccessor(agent, m), m) for m in p]
            return max(((recursive_min(s,d, 1)[0], m) for s,m in successors), key = lambda x : x[0])
        
        def recursive_min(gs, d, a):
            if (a == gs.getNumAgents()):
                return recursive_max(gs, d-1, 0)
            
            p = gs.getLegalActions(a)
            # if Directions.STOP in p:
            #     p.remove(Directions.STOP)
            if (not len(p) or d == 0):
                return (self.evaluationFunction(gs), 'STOP')
            if (gs.isWin() or gs.isLose()):
                return (self.evaluationFunction(gs), a)
            successors = ((gs.generateSuccessor(a, m), m) for m in p)
            return min(((recursive_min(s, d, a + 1)[0], m) for s,m in successors), key = lambda x : x[0])
            

        return recursive_max(gameState, self.depth, 0)[1]


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState: GameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        def recursive_max(gs, d, agent, alp, bet):
            v = (float("-inf"), 'STOP')
            p = gs.getLegalActions(agent)
            if (not len(p) or d == 0):
                return (self.evaluationFunction(gs), 'STOP')
            if (gs.isWin() or gs.isLose()):
                return (self.evaluationFunction(gs), agent)
            succ = ((gs.generateSuccessor(agent, m), m) for m in p)
            for s,m in succ:
                v = max(v, (recursive_min(s, d, agent+1, alp, bet)[0], m))
                if v[0] > bet:
                    return v
                alp = max(alp, v[0])
            return v
        
        def recursive_min(gs, d, a, alp, bet):
            v = (float("inf"), 'STOP')
            if (a == gs.getNumAgents()):
                return recursive_max(gs, d-1, 0, alp, bet)

            p = gs.getLegalActions(a)
            if (not len(p) or d == 0):
                return (self.evaluationFunction(gs), 'STOP')
            if (gs.isWin() or gs.isLose()):
                return (self.evaluationFunction(gs), a)
            succ = ((gs.generateSuccessor(a, m), m) for m in p)
            for s,m in succ:
                v = min(v, (recursive_min(s, d, a + 1, alp, bet)[0], m))
                if v[0] < alp:
                    return v
                bet = min(bet, v[0])
            return v
        
        return recursive_max(gameState, self.depth, 0, float("-inf"), float("inf"))[1]

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState: GameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        def recursive_max(gs, d, agent):
            p = gs.getLegalActions(agent)
            if (not len(p) or d == 0 or gs.isWin() or gs.isLose()):
                return self.evaluationFunction(gs)
            successors = [gs.generateSuccessor(agent, m) for m in p]
            # print("In max ; Calling recursive min function, at depth ", d, ". On our first ghost.")
            output = max(recursive_min(s,d, 1) for s in successors)
            return output
        
        def recursive_min(gs, d, a):
            if (d == 0 or gs.isWin() or gs.isLose()):
                return self.evaluationFunction(gs)
            p = gs.getLegalActions(a)
            successors = (gs.generateSuccessor(a, m) for m in p)
            summation = 0
            total = 0
            for succ in successors:
                if (a == gs.getNumAgents() - 1):
                    # print("In min; Finished with our ghost agents, moving on to recursive max function, at depth ", d-1, ". On pacman")
                    summation += recursive_max(succ, d-1, 0)
                else:
                    # print("In min; Calling recursive min function on next ghost, at depth ", d, ". On agent ", a+1)
                    summation += recursive_min(succ, d, a + 1)
                total += 1
            ret = float(summation) / total
            return ret
            
        layer1Actions = gameState.getLegalActions()
        val, move = -999999, Directions.STOP
        for layer1Action in layer1Actions:
            # print("Outer loop : on action, ", layer1Action)
            succ = gameState.generateSuccessor(0, layer1Action)
            # print("Calling recurisve max on our action above ^")
            ourVal = recursive_max(succ, self.depth, 0)
            if ourVal > val:
                val = ourVal
                move = layer1Action
        return move
        

def betterEvaluationFunction(currentGameState: GameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    Our evaluation function is based on three criteria - ghosts, food, and pellets.
    If the timer is running and the ghosts can be eaten, the algorithm prioritizes going for 
    ghosts that can be eaten - if they are out of the viable range, Pacman will try to ignore them.
    If the ghosts cannot be eaten, we want to stay a healthy distance away from them.
    For the food, assuming there is food, we want to find the successor game state which has the most food
    at the closest distance.
    For the pellets, it is the same deal as the food!
    """
    currentPos = currentGameState.getPacmanPosition()
    currentFood = currentGameState.getFood()
    currentGhosts = currentGameState.getGhostStates()
    ghostDistance_Scared = [(ghostState.getPosition(), ghostState.scaredTimer) for ghostState in currentGhosts]
    evaluation = 0
    capsules  = currentGameState.getCapsules()
    
    evaluation = 0
    for ghostPos, timer in ghostDistance_Scared:
        if timer:
            if manhattanDistance(currentPos, ghostPos) <= timer:
                evaluation += (timer - manhattanDistance(currentPos, ghostPos)) # rewarding being closer:
        else:
            evaluation -= 0.5 * max(4 - manhattanDistance(currentPos, ghostPos), 0) # rewarding the ghosts being farther!
    
    x = currentPos[0]
    y = currentPos[1]

    if currentFood.asList():
        for xs in range(x-1, x+2):
            for ys in range(y-1, y+2):
                if (xs >= 0 and xs <= len(currentFood.asList()) and ys >=0 and ys <= len(currentFood.asList()[0])):
                    evaluation += 0.3 if currentFood[xs][ys] else 0
    
    if capsules:
        for cap in capsules:
            threshold = 5
            if (manhattanDistance(currentPos, cap) < threshold):
                evaluation += 0.75*manhattanDistance(currentPos, cap) < threshold
        
    return currentGameState.getScore() + evaluation


    

# Abbreviation
better = betterEvaluationFunction
