package uk.co.rbrt.springdatanativestreaming;

public enum CaptureTraceTag
{
    NORMAL(false),
    ANOMALY(true),
    MAJOR_BASELINE(false),
    MAINTENANCE_BASELINE(false),
    FAULT(true),
    ALERTED(true),
    MAINTENANCE(true),
    UNPROCESSED(false);

    private final boolean bad;

    CaptureTraceTag(boolean bad)
    {
        this.bad = bad;
    }

    public boolean isBad()
    {
        return bad;
    }
}