from regex import F
import nn

class PerceptronModel(object):
    def __init__(self, dimensions):
        """
        Initialize a new Perceptron instance.

        A perceptron classifies data points as either belonging to a particular
        class (+1) or not (-1). `dimensions` is the dimensionality of the data.
        For example, dimensions=2 would mean that the perceptron must classify
        2D points.
        """
        self.w = nn.Parameter(1, dimensions)

    def get_weights(self):
        """
        Return a Parameter instance with the current weights of the perceptron.
        """
        return self.w

    def run(self, x):
        """
        Calculates the score assigned by the perceptron to a data point x.

        Inputs:
            x: a node with shape (1 x dimensions)
        Returns: a node containing a single number (the score)
        """
        "*** YOUR CODE HERE ***"
        return nn.DotProduct(self.w, x)

    def get_prediction(self, x):
        """
        Calculates the predicted class for a single data point `x`.

        Returns: 1 or -1
        """
        "*** YOUR CODE HERE ***"
        if nn.as_scalar(self.run(x)) >= 0:
            return 1
        else:
            return 0-1


    def train(self, dataset):
        """
        Train the perceptron until convergence.
        """
        "*** YOUR CODE HERE ***"
        terminate = False
        while (not terminate):
            bs1 = dataset.iterate_once(batch_size=1)
            terminate = True
            for x,y in bs1:
                y_pred = self.get_prediction(x)
                y_as = nn.as_scalar(y)
                if y_pred != y_as:
                    terminate = False
                    factor = y_as*x.data
                    self.w.update(nn.Constant(factor), 1)
                
                

class RegressionModel(object):
    """
    A neural network model for approximating a function that maps from real
    numbers to real numbers. The network should be sufficiently large to be able
    to approximate sin(x) on the interval [-2pi, 2pi] to reasonable precision.
    """
    def __init__(self):
        # Initialize your model parameters here
        "*** YOUR CODE HERE ***"
        """
        f(x) = relu(x*W1 + b1)*W2 + b2
        W = parameter matrices, B = parameter vectors
        W1 is i x h, i = dimension of x, h = hidden layer size
        B1 is size h
        """
        self.biases = []
        self.weights = []
        self.learning_rate = 0.015
        self.weights.append(nn.Parameter(1,64))
        self.biases.append(nn.Parameter(1,64))
        self.weights.append(nn.Parameter(64,16))
        self.biases.append(nn.Parameter(1,16))
        self.weights.append(nn.Parameter(16,1))
        self.biases.append(nn.Parameter(1,1))
        self.params = [self.weights[0], self.biases[0], self.weights[1], self.biases[1], self.weights[2], self.biases[2]]
        self.batch_size = 1

    def run(self, x):
        """
        Runs the model for a batch of examples.

        Inputs:
            x: a node with shape (batch_size x 1)
        Returns:
            A node with shape (batch_size x 1) containing predicted y-values
        """
        "*** YOUR CODE HERE ***"
        bias_0 = nn.AddBias(nn.Linear(x, self.weights[0]), self.biases[0])
        layer_0 = nn.ReLU(bias_0)
        bias_1 = nn.AddBias(nn.Linear(layer_0, self.weights[1]), self.biases[1])
        layer_1 = nn.ReLU(bias_1)
        return nn.AddBias(nn.Linear(layer_1, self.weights[2]), self.biases[2])
        

    def get_loss(self, x, y):
        """
        Computes the loss for a batch of examples.

        Inputs:
            x: a node with shape (batch_size x 1)
            y: a node with shape (batch_size x 1), containing the true y-values
                to be used for training
        Returns: a loss node
        """
        "*** YOUR CODE HERE ***"
        return nn.SquareLoss(self.run(x), y)

    def train(self, dataset):
        """
        Trains the model.
        """
        "*** YOUR CODE HERE ***"
        tl = 100000000
        while tl > 0.012:
            bs1 = dataset.iterate_once(batch_size=self.batch_size)
            for x,y in bs1:
                tl = self.get_loss(x,y)
                tl_as = nn.as_scalar(tl)
                gradient = nn.gradients(tl, self.params)
                tl = tl_as
                i = 0
                for param in self.params:
                    param.update(gradient[i], -self.learning_rate)
                    i += 1

            

class DigitClassificationModel(object):
    """
    A model for handwritten digit classification using the MNIST dataset.

    Each handwritten digit is a 28x28 pixel grayscale image, which is flattened
    into a 784-dimensional vector for the purposes of this model. Each entry in
    the vector is a floating point number between 0 and 1.

    The goal is to sort each digit into one of 10 classes (number 0 through 9).

    (See RegressionModel for more information about the APIs of different
    methods here. We recommend that you implement the RegressionModel before
    working on this part of the project.)
    """
    def __init__(self):
        # Initialize your model parameters here
        "*** YOUR CODE HERE ***"
        self.biases = []
        self.weights = []
        self.learning_rate = 0.101
        self.weights.append(nn.Parameter(784,294))
        self.biases.append(nn.Parameter(1,294))
        self.weights.append(nn.Parameter(294,49))
        self.biases.append(nn.Parameter(1,49))
        self.weights.append(nn.Parameter(49,10))
        self.biases.append(nn.Parameter(1,10))
        self.params = [self.weights[0], self.biases[0], self.weights[1], self.biases[1], self.weights[2], self.biases[2]]
        self.batch_size = 50

    def run(self, x):
        """
        Runs the model for a batch of examples.

        Your model should predict a node with shape (batch_size x 10),
        containing scores. Higher scores correspond to greater probability of
        the image belonging to a particular class.

        Inputs:
            x: a node with shape (batch_size x 784)
        Output:
            A node with shape (batch_size x 10) containing predicted scores
                (also called logits)
        """
        "*** YOUR CODE HERE ***"
        # layer == 0
        bias_0 = nn.AddBias(nn.Linear(x, self.weights[0]), self.biases[0])
        layer_0 = nn.ReLU(bias_0)
        # layer == 1
        bias_1 = nn.AddBias(nn.Linear(layer_0, self.weights[1]), self.biases[1])
        layer_1 = nn.ReLU(bias_1)
        # layer == 2
        return nn.AddBias(nn.Linear(layer_1, self.weights[2]), self.biases[2])

    def get_loss(self, x, y):
        """
        Computes the loss for a batch of examples.

        The correct labels `y` are represented as a node with shape
        (batch_size x 10). Each row is a one-hot vector encoding the correct
        digit class (0-9).

        Inputs:
            x: a node with shape (batch_size x 784)
            y: a node with shape (batch_size x 10)
        Returns: a loss node
        """
        "*** YOUR CODE HERE ***"
        return nn.SoftmaxLoss(self.run(x), y)

    def train(self, dataset):
        """
        Trains the model.
        """
        "*** YOUR CODE HERE ***"
        tl = 100000000
        validation_threshold = 0.98
        validation_accuracy = 0
        while validation_accuracy < validation_threshold:
            bs1 = dataset.iterate_once(batch_size=self.batch_size)
            for x,y in bs1:
                tl = self.get_loss(x,y)
                tl_as = nn.as_scalar(tl)
                gradient = nn.gradients(tl, self.params)
                tl = tl_as
                i = 0
                for i in range(3):
                    self.params[2*i].update(gradient[2*i], -self.learning_rate)
                    self.params[2*i+1].update(gradient[2*i+1], -self.learning_rate)
            validation_accuracy = dataset.get_validation_accuracy()
            print(validation_accuracy)

class LanguageIDModel(object):
    """
    A model for language identification at a single-word granularity.

    (See RegressionModel for more information about the APIs of different
    methods here. We recommend that you implement the RegressionModel before
    working on this part of the project.)
    """
    def __init__(self):
        # Our dataset contains words from five different languages, and the
        # combined alphabets of the five languages contain a total of 47 unique
        # characters.
        # You can refer to self.num_chars or len(self.languages) in your code
        self.num_chars = 47
        self.languages = ["English", "Spanish", "Finnish", "Dutch", "Polish"]

        # Initialize your model parameters here
        "*** YOUR CODE HERE ***"
        self.weights = [nn.Parameter(self.num_chars, 512), nn.Parameter(self.num_chars, 512), nn.Parameter(512, 512), nn.Parameter(512, len(self.languages))]
        self.biases = [nn.Parameter(1, 512), nn.Parameter(1, 512), nn.Parameter(1, len(self.languages))]
        self.learning_rate = 0.1
        self.batch_size = 50
        self.params = [self.weights[0], self.biases[0], self.weights[1], self.weights[2], self.biases[1], self.weights[3], self.biases[2]]


    def run(self, xs):
        """
        Runs the model for a batch of examples.

        Although words have different lengths, our data processing guarantees
        that within a single batch, all words will be of the same length (L).

        Here `xs` will be a list of length L. Each element of `xs` will be a
        node with shape (batch_size x self.num_chars), where every row in the
        array is a one-hot vector encoding of a character. For example, if we
        have a batch of 8 three-letter words where the last word is "cat", then
        xs[1] will be a node that contains a 1 at position (7, 0). Here the
        index 7 reflects the fact that "cat" is the last word in the batch, and
        the index 0 reflects the fact that the letter "a" is the inital (0th)
        letter of our combined alphabet for this task.

        Your model should use a Recurrent Neural Network to summarize the list
        `xs` into a single node of shape (batch_size x hidden_size), for your
        choice of hidden_size. It should then calculate a node of shape
        (batch_size x 5) containing scores, where higher scores correspond to
        greater probability of the word originating from a particular language.

        Inputs:
            xs: a list with L elements (one per character), where each element
                is a node with shape (batch_size x self.num_chars)
        Returns:
            A node with shape (batch_size x 5) containing predicted scores
                (also called logits)
        """
        "*** YOUR CODE HERE ***"
        internal_linear = nn.Linear(xs[0], self.weights[0])
        added_bias = nn.AddBias(internal_linear, self.biases[0])
        hidden = nn.ReLU(added_bias)
        for i in range(1,len(xs)):
            currChar = xs[i]
            lin1 = nn.Linear(currChar, self.weights[1])
            lin2 = nn.Linear(hidden, self.weights[2])
            lin12 = nn.Add(lin1, lin2)
            addedLin12 = nn.AddBias(lin12, self.biases[1])
            hidden = nn.ReLU(addedLin12)
        finalLinear = nn.Linear(hidden, self.weights[3])
        return nn.AddBias(finalLinear, self.biases[2])


    def get_loss(self, xs, y):
        """
        Computes the loss for a batch of examples.

        The correct labels `y` are represented as a node with shape
        (batch_size x 5). Each row is a one-hot vector encoding the correct
        language.

        Inputs:
            xs: a list with L elements (one per character), where each element
                is a node with shape (batch_size x self.num_chars)
            y: a node with shape (batch_size x 5)
        Returns: a loss node
        """
        return nn.SoftmaxLoss(self.run(xs),y)

    def train(self, dataset):
        """
        Trains the model.
        """
        "*** YOUR CODE HERE ***"
        tl = 100000000
        validation_threshold = 0.85
        validation_accuracy = 0
        while validation_accuracy < validation_threshold:
            bs1 = dataset.iterate_once(batch_size=self.batch_size)
            for x,y in bs1:
                tl = self.get_loss(x,y)
                tl_as = nn.as_scalar(tl)
                gradient = nn.gradients(tl, self.params)
                tl = tl_as
                i = 0
                for i in range(3):
                    self.params[2*i].update(gradient[2*i], -self.learning_rate)
                    self.params[2*i+1].update(gradient[2*i+1], -self.learning_rate)
            validation_accuracy = dataset.get_validation_accuracy()
            print(validation_accuracy)
