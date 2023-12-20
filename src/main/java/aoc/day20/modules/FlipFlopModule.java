package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;
import aoc.day20.PulseType;

import java.util.ArrayList;
import java.util.List;

public class FlipFlopModule extends CommunicationModule {


    public FlipFlopModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
        state.clear();
        state.add(false);

    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        List<Pulse> responses = new ArrayList<>();
        if (pulse.pulseType() == PulseType.LOW) {
            flipState();
            if (isOn()) {
                for (CommunicationModule m : outputModules) {
                    responses.add(new Pulse(PulseType.HIGH, this, m));
                }
            } else {
                for (CommunicationModule m : outputModules) {
                    responses.add(new Pulse(PulseType.LOW, this, m));
                }
            }
        }
        return responses;
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new FlipFlopModule(getName(), mw);
    }

    private void flipState() {
        state.set(0, !state.get(0));
    }

    public boolean isOn() {
        return state.get(0);
    }
}
