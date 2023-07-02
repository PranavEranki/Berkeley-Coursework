## Plan and Document (old dev process)
- Make a plan before coding, write detailed documentation on all phases of the plan, and progress is measured against the plan ; changes to the plan must be retroactively updated. 
- Waterfall dev process ; each stage is fully completed before moving on, earlier bug catch = cheaper to fix.
    - Stage 1: Requirements analysis and spec
    - Stage 2: architectural design
    - Stage 3: Implementation and integration
    - Stage 4: Verification / testing after the fact
    - Stage 5: Put into operation, maintain it

## Other options...
- Spiral lifecycle, Rational Unified Process
- Motivation for stuff like Agile: if I build a prototype now, and wait X months for customer to respond, that is the risk I take that something will not work or meet desires.

## Agile
- Agile manifesto
    - individuals and interactions over processes and tools
    - working software over comprehensive documentation
    - customer collaboration over contract negotiation
    - respond to change, be dynamic, don't focus on the plan    
- Agile
    - Emphasizes test driven development
    - Phases (cyclical):
        - Talk to customer
        - Lo-fi UI Mockup
        - User stories and scenarios
        - Consider legacy code and design patterns before proceeding
        - Behavior driven design, user stories
        - Test first development (have unit / functional tests)
        - Measure velocity, see how much you can get done.
        - Deploy
- XP version of Agile
    - Makes short iterations (i.e. 1 week)
    - Emphasizes simplicity - working basic functionality over having lots of features
    - Test your code all the time... what will someone coming from the outside expect your code to do?
    - Code reviews!

### Software quality assurance
- Quality = fitness for use
    - Quality assurance = processes/standards to produce product and improve quality over time
    - satisfies customers' needs... easy to use, gets correct answers, doesn't crash
    - Easy for developers to debug and enhance, and developers test their own stuff
- Verification = did you build the thing right? did you meet specs?
- Validation = did you build the right thing? is this what the customer wants, and is your built spec correct?
- Code coverage testing
    - Unit tests: single method
    - Module / functional test: across units
    - Integration test: checks interfaces between units have correct assumptions and communicate right
    - Acceptance test: integrated program (i.e. new feature) meets specifications, is error-free, etc

### Productivity
- Basic techniques
    - Use code that writes code, i.e. synthesis/generators
    - Clarity via conciseness/comments
        - Raise level of abstraction
        - Strive for readability
    - Reuse code (write functional modules, etc)
        - Procedures/functions (obvious)
        - Reuse collections of tasks - libraries
        - Interfaces & mix-ins ; reuse behaviors independently of implementation, i.e. sort comparable.
    - Automation and tools
- Don't Repeat Yourself
    - Refactor code to extract commonality
    - Centralize knowledge of housekeeping by automating with scripts


### SaaS
- Helps as data is stored safely in cloud, and there is one copy of SW (no compatibility issues, can beta test new features, upgrades across the board are easy), helps continuous customer rollouts and integration.
    - Direct comparison: Service oriented architecture
        - The trademark of service oriented architecture is that services are self contained, and how it works is a black box to the user or data being submitted to it. As a result, the service should not be able to directly access another service in an SOA setting, so one service cannot access another service's data.
- SaaS Cloud Computing
    - Services on clusters are more scalable, cheaper, dependable, and need less operators than conventional servers
        - Warehouse scale computing, clusters grow from 1000 to 100,000 servers based on customer demand, so economies of scale kick in.
        - Realistically, demand won't always be at 100% of compute, so you can sell cloud computing ability ; helps resolve excess and gives business, but revolutionizes usage as now you can rent 1000 computers for 1 hour or 1 computer for 1000 hours.
        - Easy to have low total cost of ownership, easy scale up and down (elasticity)
    - You must DESIGN the potential of unlimited scalability into your software.
- Mobile devices and SaaS
    - Apps should either be "mobile-friendly" or "mobile-first" ; responsive ; interaction (mouse/touchpad vs phone)
    - Cover HTML (structure), CSS (styling), Bootstrap CSS (cooler CSS), ...
        - Good frameworks usually write assets in a hierarchical fashion, with high level components being a combination of multiple HTML elements.
        - Good frameworks provide responsive behavior that adapts the webpage content to different display sizes (i.e. phone vs. computer) 
        - Good frameworks provide accessibility support for users with disabilities; this support can be activated by assistive technologies.

### Refactoring / legacy
- 60% of SW cost is maintenance, 60% of maintenance costs is features.
- Legacy code runs every night, so it is important. 