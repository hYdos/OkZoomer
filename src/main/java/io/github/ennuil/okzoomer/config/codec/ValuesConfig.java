package io.github.ennuil.okzoomer.config.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ValuesConfig {
    public static final Codec<ValuesConfig> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.doubleRange(Double.MIN_NORMAL, Double.MAX_VALUE).fieldOf("zoom_divisor").orElse(4.0).forGetter(ValuesConfig::getZoomDivisor),
            Codec.doubleRange(Double.MIN_NORMAL, Double.MAX_VALUE).fieldOf("minimum_zoom_divisor").orElse(1.0).forGetter(ValuesConfig::getMinimumZoomDivisor),
            Codec.doubleRange(Double.MIN_NORMAL, Double.MAX_VALUE).fieldOf("maximum_zoom_divisor").orElse(50.0).forGetter(ValuesConfig::getMaximumZoomDivisor),
            Codec.intRange(0, Integer.MAX_VALUE).fieldOf("upper_scroll_step").orElse(20).forGetter(ValuesConfig::getUpperScrollStep),
            Codec.INT.fieldOf("lower_scroll_step").orElse(4).forGetter(ValuesConfig::getLowerScrollStep),
            Codec.doubleRange(Double.MIN_NORMAL, 1.0).fieldOf("smooth_multiplier").orElse(0.75).forGetter(ValuesConfig::getSmoothMultiplier),
            Codec.doubleRange(Double.MIN_NORMAL, 4.0).fieldOf("cinematic_multiplier").orElse(4.0).forGetter(ValuesConfig::getCinematicMultiplier),
            Codec.doubleRange(0.0, Double.MAX_VALUE).fieldOf("minimum_linear_step").orElse(0.125).forGetter(ValuesConfig::getMinimumLinearStep),
            Codec.doubleRange(Double.MIN_NORMAL, Double.MAX_VALUE).fieldOf("maximum_linear_step").orElse(0.25).forGetter(ValuesConfig::getMaximumLinearStep)
        )
        .apply(instance, ValuesConfig::new)
    );

    private double zoomDivisor;
    private double minimumZoomDivisor;
    private double maximumZoomDivisor;
    private int upperScrollStep;
    private int lowerScrollStep;
    private double smoothMultiplier;
    private double cinematicMultiplier;
    private double minimumLinearStep;
    private double maximumLinearStep;

    public ValuesConfig(
        double zoomDivisor,
        double minimumZoomDivisor,
        double maximumZoomDivisor,
        int upperScrollStep,
        int lowerScrollStep,
        double smoothMultiplier,
        double cinematicMultiplier,
        double minimumLinearStep,
        double maximumLinearStep
    ) {
        this.zoomDivisor = zoomDivisor;
        this.minimumZoomDivisor = minimumZoomDivisor;
        this.maximumZoomDivisor = maximumZoomDivisor;
        this.upperScrollStep = upperScrollStep;
        this.lowerScrollStep = lowerScrollStep;
        this.smoothMultiplier = smoothMultiplier;
        this.cinematicMultiplier = cinematicMultiplier;
        this.minimumLinearStep = minimumLinearStep;
        this.maximumLinearStep = maximumLinearStep;
    }

    public ValuesConfig() {
        this.zoomDivisor = 4.0;
        this.minimumZoomDivisor = 1.0;
        this.maximumZoomDivisor = 50.0;
        this.upperScrollStep = 10;
        this.lowerScrollStep = 5;
        this.smoothMultiplier = 0.75;
        this.cinematicMultiplier = 4.0;
        this.minimumLinearStep = 0.125;
        this.maximumLinearStep = 0.25;
    }

    public double getZoomDivisor() {
        return zoomDivisor;
    }

    public void setZoomDivisor(double zoomDivisor) {
        this.zoomDivisor = zoomDivisor;
    }

    public double getMinimumZoomDivisor() {
        return minimumZoomDivisor;
    }

    public void setMinimumZoomDivisor(double minimumZoomDivisor) {
        this.minimumZoomDivisor = minimumZoomDivisor;
    }

    public double getMaximumZoomDivisor() {
        return maximumZoomDivisor;
    }

    public void setMaximumZoomDivisor(double maximumZoomDivisor) {
        this.maximumZoomDivisor = maximumZoomDivisor;
    }

    public int getUpperScrollStep() {
        return upperScrollStep;
    }

    public void setUpperScrollStep(int upperScrollStep) {
        this.upperScrollStep = upperScrollStep;
    }

    public int getLowerScrollStep() {
        return lowerScrollStep;
    }

    public void setLowerScrollStep(int lowerScrollStep) {
        this.lowerScrollStep = lowerScrollStep;
    }

    public double getSmoothMultiplier() {
        return smoothMultiplier;
    }

    public void setSmoothMultiplier(double smoothMultiplier) {
        this.smoothMultiplier = smoothMultiplier;
    }

    public double getCinematicMultiplier() {
        return cinematicMultiplier;
    }

    public void setCinematicMultiplier(double cinematicMultiplier) {
        this.cinematicMultiplier = cinematicMultiplier;
    }

    public double getMaximumLinearStep() {
        return maximumLinearStep;
    }

    public void setMaximumLinearStep(double maximumLinearStep) {
        this.maximumLinearStep = maximumLinearStep;
    }

    public double getMinimumLinearStep() {
        return minimumLinearStep;
    }

    public void setMinimumLinearStep(double minimumLinearStep) {
        this.minimumLinearStep = minimumLinearStep;
    }
}
