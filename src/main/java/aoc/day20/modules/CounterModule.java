package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;
import aoc.day20.PulseType;

import java.util.List;

public class CounterModule extends CommunicationModule {

    private PulseType latest;

    public CounterModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
        state.add(false);
    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        if (pulse.pulseType() != latest) {
            if (latest != null) {
                state.set(0, true);
            }
            latest = pulse.pulseType();
        }
        return List.of();
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new CounterModule(getName(), mw);
    }

    public boolean isHit() {
        return getState().get(0);
    }

    public PulseType getLatest() {
        return latest;
    }

    public void clear() {
        state.set(0, false);
    }
}
