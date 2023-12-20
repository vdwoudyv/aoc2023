package aoc.day20;

import aoc.day20.modules.CommunicationModule;

public record Pulse(PulseType pulseType, CommunicationModule source, CommunicationModule target) {

    @Override
    public String toString() {
        return source.getName() + pulseType.getSymbol() + target.getName();
    }
}
