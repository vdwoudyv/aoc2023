package aoc.day20.modules;

import aoc.day20.ModuleWorld;
import aoc.day20.Pulse;
import aoc.day20.PulseType;

import java.util.List;

public class ButtonModule extends CommunicationModule {

    public ButtonModule(String name, ModuleWorld world) {
        super(name, world);
    }

    @Override
    protected void initState() {
        // No state
    }

    @Override
    protected List<Pulse> calculatePulseResponse(Pulse pulse) {
        return List.of(new Pulse(PulseType.LOW, this, outputModules.get(0)));
    }

    @Override
    public CommunicationModule cloneToDifferentWorld(ModuleWorld mw) {
        return new ButtonModule(getName(), mw);
    }
}
