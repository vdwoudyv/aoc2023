package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;

import java.util.ArrayList;
import java.util.List;

public class BroadcastCommunicationModule extends CommunicationModule {

    public BroadcastCommunicationModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
        // No state
    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        List<Pulse> response = new ArrayList<>();
        for (CommunicationModule m: outputModules) {
            response.add(new Pulse(pulse.pulseType(), this, m));
        }
        return response;
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new BroadcastCommunicationModule(getName(), mw);
    }
}
