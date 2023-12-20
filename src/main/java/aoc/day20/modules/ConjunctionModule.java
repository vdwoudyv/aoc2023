package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;
import aoc.day20.PulseType;

import java.util.ArrayList;
import java.util.List;

public class ConjunctionModule extends CommunicationModule {
    public ConjunctionModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
        state.clear();
        for (CommunicationModule m: inputModules) {
            state.add(false); // False is low, True is High
        }
    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        // Update state for that input module
        int index = inputModules.indexOf(pulse.source());
        state.set(index, pulse.pulseType() == PulseType.HIGH);
        PulseType pt = allHigh() ? PulseType.LOW : PulseType.HIGH;
        List<Pulse> response = new ArrayList<>();
        for (CommunicationModule m: outputModules) {
            response.add(new Pulse(pt, this, m));
        }
        return response;
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new ConjunctionModule(getName(), mw);
    }

    private boolean allHigh() {
        return state.stream().allMatch(s->s);
    }
}
