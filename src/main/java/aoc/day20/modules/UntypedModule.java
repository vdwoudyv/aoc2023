package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;

import java.util.List;

public class UntypedModule extends CommunicationModule {

    public UntypedModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        return List.of();
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new UntypedModule(getName(), mw);
    }
}
