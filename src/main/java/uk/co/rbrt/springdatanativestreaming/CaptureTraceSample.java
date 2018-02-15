package uk.co.rbrt.springdatanativestreaming;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Embeddable
public class CaptureTraceSample
{
    @Column(nullable = false)
    private int sampleNumber;

    @Column(nullable = false)
    private double value;

    public CaptureTraceSample(int sampleNumber, double value)
    {
        this.sampleNumber = sampleNumber;
        this.value = value;
    }
}
