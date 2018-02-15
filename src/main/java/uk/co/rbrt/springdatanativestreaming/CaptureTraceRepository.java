package uk.co.rbrt.springdatanativestreaming;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.stream.Stream;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

@Transactional(readOnly = true)
public interface CaptureTraceRepository extends JpaRepository<CaptureTrace, Long>
{
    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(nativeQuery = true,
           value = "SELECT " +
                   "  ct.id AS id, " +
                   "  ct.date AS date, " +
                   "  ct.comments as comments, " +
                   "  ct.direction as direction, " +
                   "  ct.start_trigger_time as startTriggerTime, " +
                   "  ct.end_trigger_time as endTriggerTime, " +
                   "  ct.max_sample as maxSample, " +
                   "  ct.min_sample as minSample, " +
                   "  ct.sample_count as sampleCount, " +
                   "  ct.tag as tag, " +
                   "  ct.time_delta as timeDelta, " +
                   "  cts.sample_number as sampleNumber, " +
                   "  cts.value as value " +
                   "FROM " +
                   "  capture_trace ct " +
                   "  JOIN capture_trace_sample cts ON ct.id = cts.capture_trace " +
                   "ORDER BY " +
                   "  ct.date DESC, " +
                   "  ct.id DESC, " +
                   "  cts.sample_number")
    Stream<Object[]> findStream();
}
