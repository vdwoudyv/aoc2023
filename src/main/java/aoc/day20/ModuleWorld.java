package aoc.day20;

import aoc.day20.modules.*;
import aoc.util.Tuple;
import org.w3c.dom.css.Counter;


import java.util.*;
import java.util.stream.Collectors;

/**
 * A collection of communicationmodules.
 */
public class ModuleWorld {

    private final List<Pulse> activePulseQueue = new ArrayList<>();
    private final List<Pulse> executedQueue = new ArrayList<>();

    private final Map<String, CommunicationModule> moduleMap = new HashMap<>();
    private final Map<String, ButtonPressImpact> stateMap = new HashMap<>();
    private String currentState;

    /**
     * Base constructor, initializing a world using the text input.
     * @param moduleConfigurations the different lines in the text input.
     */
    public ModuleWorld(List<String> moduleConfigurations) {
        BroadcastCommunicationModule broadCastModule = new BroadcastCommunicationModule("broadcaster", this);
        ButtonModule buttonModule = new ButtonModule("button", this);
        buttonModule.addOutputModule(broadCastModule);
        broadCastModule.addInputModule(buttonModule);
        moduleMap.put("button", buttonModule);
        moduleMap.put("broadcaster", broadCastModule);

        // Create modules
        for (String moduleConfig: moduleConfigurations) {
            String name = moduleConfig.substring(0, moduleConfig.indexOf(' ')).trim();
            if (!name.equals("broadcaster")) {
                String moduleName = name.substring(1);
                if (name.startsWith("%")) {
                    moduleMap.put(moduleName, new FlipFlopModule(moduleName, this));
                } else if (name.startsWith("&")) {
                    moduleMap.put(moduleName, new ConjunctionModule(moduleName, this));
                }
            }
        }

        // Link all modules
        for (String moduleConfig: moduleConfigurations) {
            String sourceName = moduleConfig.substring(0,moduleConfig.indexOf(' ')).trim();
            if (sourceName.startsWith("%") || sourceName.startsWith("&")) {
                sourceName = sourceName.substring(1);
            }
            List<String> targetNames = Arrays.stream(moduleConfig.substring(moduleConfig.indexOf(">")+1)
                    .split(",")).map(String::trim).toList();
            CommunicationModule source = getModule(sourceName);
            for (String targetName: targetNames) {
                CommunicationModule target = getModule(targetName);
                source.addOutputModule(target);
                target.addInputModule(source);
            }
        }
        currentState = captureState();
    }

    /**
     * Creates a new world based on a number of existing modules (of a different world). This constructor will create a
     * clone of each of the given modules and preserve their relations. One single module, the output, will be replaced
     * by a newly created outputmodule, and will therefore change type. All other modules keep their type.
     * @param subSystemModules all modules to add to this world
     * @param output the ending module
     */
    public ModuleWorld(List<CommunicationModule> subSystemModules, CommunicationModule output) {
        // Create all modules
        moduleMap.put(output.getName(), new CounterModule(output.getName(), this));
        for (CommunicationModule module: subSystemModules) {
            if (module != output) {
                moduleMap.put(module.getName(), module.cloneToDifferentWorld(this));
            }
        }

        for (CommunicationModule source: subSystemModules) {
            CommunicationModule sourceCounterpart = moduleMap.get(source.getName());
            for (CommunicationModule target: source.getOutputModules()) {
                CommunicationModule targetCounterpart = moduleMap.get(target.getName());
                if (targetCounterpart != null) {
                    sourceCounterpart.addOutputModule(targetCounterpart);
                    targetCounterpart.addInputModule(sourceCounterpart);
                }
            }
        }
        currentState = captureState();
    }


    /**
     * Adds a list of pulses to be processed. Typically executed by a module in response to the execution of a pulse of
     * that module. Will be added to a queue for future execution.
     * @param pulses the pulses to add to the queue.
     */
    public void addPulses(List<Pulse> pulses) {
        activePulseQueue.addAll(pulses);
    }

    /**
     * Executes all pulses in the queue until the queue is empty.
     */
    public void execute() {
        while (!activePulseQueue.isEmpty()) {
            Pulse p = activePulseQueue.remove(0);
            p.target().processPulse(p);
            executedQueue.add(p);
        }
    }

    /**
     * Gets the module with the given name. If no module exists with that name, it is added to this world as an
     * untyped module.
     * @param name the name of the module to locate
     * @return the module with the given name.
     */
    public CommunicationModule getModule(String name) {
        CommunicationModule response = moduleMap.get(name);
        if (response == null) {
            if (name.equals("rx")) {
                OutputModule rx = new OutputModule(name, this);
                moduleMap.put(name, rx);
                return rx;
            } else {
                CommunicationModule nieuw = new UntypedModule(name, this);
                moduleMap.put(name, nieuw);
                return nieuw;
            }
        }
        return response;
    }

    /**
     * @return a String representation of the state of the world (which is the state of each module)
     */
    public String getCurrentState() {
        return currentState;
    }

    /**
     * Press the inputbutton once.
     * @return The impact this had: the initial state, the resulting state, and the number of low and high signals.
     */
    public ButtonPressImpact pushButton() {
        if (! stateMap.containsKey(currentState)) {
            activePulseQueue.add(new Pulse(PulseType.LOW, getModule("button"), getModule("broadcaster")));
            execute();
            String endState = captureState();
            long nbHigh = executedQueue.stream().filter(p -> p.pulseType() == PulseType.HIGH).count();
            long nbLow = executedQueue.stream().filter(p -> p.pulseType() == PulseType.LOW).count();
            ButtonPressImpact impact = new ButtonPressImpact(currentState, endState, nbLow, nbHigh);
            stateMap.put(currentState, impact);
            currentState = endState;
            executedQueue.clear();
            return impact;
        } else {
            ButtonPressImpact impact = stateMap.get(currentState);
            currentState = impact.targetState();
            return impact;
        }
    }

    /**
     * @return a representation of the current state.
     */
    private String captureState() {
        return moduleMap.values().stream().sorted().map(CommunicationModule::exportState).collect(Collectors.joining("_"));
    }

    /**
     * Represents the world as a plantuml state diagram and prints this to console.
     */
    public void printPlanUml() {
        System.out.println("@startuml");
        for (CommunicationModule m: moduleMap.values()) {
            for (CommunicationModule t: m.getOutputModules()) {
                String start = m.getName().equals("button") ? "[*]" : m.getName();
                if (m instanceof FlipFlopModule) {
                    start = start + " #red";
                } else if (m instanceof ConjunctionModule) {
                    start = start + " #green";
                }
                String stop = t.getName().equals("rx") ? "[*]" : t.getName();
                System.out.println(start + "-->" + stop);
            }
        }
        System.out.println("@enduml");
    }

    /**
     * @return all subsystems from this world. this method is specific to the AOC exercise and will only work if the
     * outputmodule is preceded by a ConjunctionModule that each represent an entire subset of the nodes.
     */
    public List<ModuleWorld> getSubsystems() {
        CommunicationModule outputModule = getOutputModule();
        List<CommunicationModule> seeds = outputModule.getInputModules();
        if (outputModule.getInputModules().size() == 1) {
            CommunicationModule seed = seeds.get(0);
            List<ModuleWorld> modules = new ArrayList<>();
            for (CommunicationModule seedInput: seed.getInputModules()) {
                List<CommunicationModule> subSystemModules = getReachingSet(seedInput);
                modules.add( new ModuleWorld(subSystemModules, seedInput));
            }
            return modules;
        } else {
            return List.of();
        }

    }

    public Long calculateButtonsUntilOutput() {
        CounterModule cm = getCounterModule();
        long count = 0;
        while (! cm.isHit()) {
            pushButton();
            count++;
        }
        return count;
    }

    /**
     * Returns all communicationmodules that can ultimately reach the given endpoint
     * @param endpoint
     * @return
     */
    private List<CommunicationModule> getReachingSet(CommunicationModule endpoint) {
        List<CommunicationModule> result = new ArrayList<>();
        for (CommunicationModule com: moduleMap.values()) {
            Set<CommunicationModule> set = new HashSet<>();
            completeReachSet(com, set);
            if (set.contains(endpoint)) {
                result.add(com);
            }
        }
        result.add(endpoint);
        return result;
    }

    private void completeReachSet(CommunicationModule start, Set<CommunicationModule> known) {
        for (CommunicationModule com: start.getOutputModules()) {
            if (! known.contains(com)) {
                known.add(com);
               completeReachSet(com, known);
            }
        }
    }

    /**
     * Returns the only outputmodule.
     */
    private OutputModule getOutputModule() {
        List<CommunicationModule> outputModules =  moduleMap.values().stream().filter(m -> m instanceof OutputModule).toList();
        if (outputModules.size() != 1) {
            throw new IllegalArgumentException("Not expected");
        } else {
            return (OutputModule) outputModules.get(0);
        }
    }

    /**
     * Returns the only outputmodule.
     */
    private CounterModule getCounterModule() {
        List<CommunicationModule> outputModules =  moduleMap.values().stream().filter(m -> m instanceof CounterModule).toList();
        if (outputModules.size() != 1) {
            throw new IllegalArgumentException("Not expected");
        } else {
            return (CounterModule) outputModules.get(0);
        }
    }
}
