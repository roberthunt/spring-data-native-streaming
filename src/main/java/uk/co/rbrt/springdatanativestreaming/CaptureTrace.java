package uk.co.rbrt.springdatanativestreaming;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CaptureTrace
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long date;

    @Column(nullable = false)
    private Double timeDelta;

    @Column(nullable = false)
    private Integer sampleCount;

    @Column
    private Double minSample;

    @Column
    private Double maxSample;

    @Column
    private Long startTriggerTime;

    @Column
    private Long endTriggerTime;

    @Column
    private Integer direction;

    @Column
    @Lob
    private String comments;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CaptureTraceTag tag = CaptureTraceTag.NORMAL;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "capture_trace_sample",
            joinColumns = @JoinColumn(name = "capture_trace")
    )
    @OrderBy("sampleNumber")
    private List<CaptureTraceSample> samples = new ArrayList<>();
}
