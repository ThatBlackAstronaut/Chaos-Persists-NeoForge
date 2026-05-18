package com.astryxion.chaospersists.compat.minecraft.world.gen;

import java.util.Random;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.synth.ImprovedNoise;

/** Legacy 1.12 octave noise used by Chaos Persists chunk providers. */
public class NoiseGeneratorOctaves {
  private final ImprovedNoise[] generators;
  private final int octaves;

  public NoiseGeneratorOctaves(Random random, int octavesIn) {
    this.octaves = octavesIn;
    this.generators = new ImprovedNoise[octavesIn];
    for (int i = 0; i < octavesIn; i++) {
      this.generators[i] = new ImprovedNoise(RandomSource.create(random.nextLong()));
    }
  }

  public double[] generateNoiseOctaves(
      double[] noise,
      int xOffset,
      int yOffset,
      int zOffset,
      int xSize,
      int ySize,
      int zSize,
      double xScale,
      double yScale,
      double zScale) {
    int total = xSize * ySize * zSize;
    if (noise == null || noise.length < total) {
      noise = new double[total];
    } else {
      for (int i = 0; i < total; i++) {
        noise[i] = 0.0;
      }
    }

    double amp = 1.0;
    double freqX = xScale;
    double freqY = yScale;
    double freqZ = zScale;
    int idx = 0;
    for (int i = 0; i < this.octaves; i++) {
      double sampleX = xOffset * freqX;
      double sampleY = yOffset * freqY;
      double sampleZ = zOffset * freqZ;
      idx = 0;
      for (int yy = 0; yy < ySize; yy++) {
        for (int zz = 0; zz < zSize; zz++) {
          for (int xx = 0; xx < xSize; xx++) {
            noise[idx++] +=
                this.generators[i].noise(sampleX + xx, sampleY + yy, sampleZ + zz) * amp;
          }
        }
      }
      amp *= 0.5;
      freqX *= 2.0;
      freqY *= 2.0;
      freqZ *= 2.0;
    }
    return noise;
  }

  /** 1.12 overload: y size 1, y offset 10, y scale 1.0. */
  public double[] generateNoiseOctaves(
      double[] noise,
      int xOffset,
      int zOffset,
      int xSize,
      int zSize,
      double xScale,
      double zScale,
      double unused) {
    return generateNoiseOctaves(
        noise, xOffset, 10, zOffset, xSize, 1, zSize, xScale, 1.0, zScale);
  }
}
