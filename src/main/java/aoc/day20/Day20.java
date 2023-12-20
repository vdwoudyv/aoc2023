package aoc.day20;

import aoc.Day;
import aoc.day20.modules.ButtonPressImpact;
import aoc.util.AocTools;
import aoc.util.Tuple;

import java.util.List;
import java.util.stream.Collectors;

public class Day20 implements Day {

    @Override
    public boolean forceTest() {
        return false;
    }

    @Override
    public String runPartOne(boolean testInput) {
        ModuleWorld world = new ModuleWorld(AocTools.read(testInput, this));
        long lows = 0;
        long highs = 0;
        for (int i = 0; i < 1000; i++){
            ButtonPressImpact result = world.pushButton();
            lows += result.nbLowPulses();
            highs += result.nbHighPulses();
        }
        return "" + (highs*lows);
    }

    /**
     * Input is constructed in a specific fashion. You can use the printPlantUml method to see how the network
     * is structured. rx is reached by a ConjunctionModule that in turn is connected to 4 other conjunction modules.
     * Each of these 4 has only a single input (basically it forwards) which in turn is again a Conjunctionmodule.
     * These latter are the only output of an entire subsystem of modules. So basically, our strategy is the following:
     * for each of the inputs of the conjunctionmodule that precedes our RX module, we identify a new subsystem where
     * we replace the conjunctionmodule with an countermodule. We then take the least common multiple.
     */
    @Override
    public String runPartTwo(boolean testInput) {
        ModuleWorld world = new ModuleWorld(AocTools.read(testInput, this));
        List<ModuleWorld> sub = world.getSubsystems();
        return "" + AocTools.leastCommonMultipleLong(
                sub.stream()
                        .map(ModuleWorld::calculateButtonsUntilOutput)
                        .collect(Collectors.toList()));
    }
}
