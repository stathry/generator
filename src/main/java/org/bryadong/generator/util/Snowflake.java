package org.bryadong.generator.util;

public class Snowflake
{
  public static final int NODE_SHIFT = 10;
  public static final int SEQ_SHIFT = 12;
  public static final short MAX_NODE = 1024;
  public static final short MAX_SEQUENCE = 4096;
  private short sequence;
  private long referenceTime;
  private int node;

  public Snowflake(int node)
  {
    if ((node < 0) || (node > 1024)) {
      throw new IllegalArgumentException(String.format("node must be between %s and %s", new Object[] { "0", "1024" }));
    }
    this.node = node;
  }

  public long next()
  {
    long currentTime = System.currentTimeMillis();
    long counter;
    synchronized (this)
    {
      if (currentTime < this.referenceTime)
        throw new RuntimeException(String.format("Last referenceTime %s is after reference time %s", new Object[] { Long.valueOf(this.referenceTime), Long.valueOf(currentTime) }));
      if (currentTime > this.referenceTime) {
        this.sequence = 0;
      }
      else if (this.sequence < 4096)
        this.sequence = ((short)(this.sequence + 1));
      else {
        throw new RuntimeException("Sequence exhausted at " + this.sequence);
      }

      counter = this.sequence;
      this.referenceTime = currentTime;
    }

    return currentTime << 10 << 12 | this.node << 12 | counter;
  }
}
