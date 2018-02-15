package db.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class V2__Capture_Data implements SpringJdbcMigration
{
    private static final int NUM_CAPTURES = 7000;
    private static final int NUM_SAMPLES_PER_CAPTURE = 300;
    private static final long DATE = LocalDateTime.of(2018, 1, 1, 12, 30).toInstant(ZoneOffset.UTC).toEpochMilli();

    @Override
    public void migrate(JdbcTemplate jdbcTemplate) throws Exception
    {
        for (int captureId = 1; captureId <= NUM_CAPTURES; captureId++)
        {

            jdbcTemplate.update("INSERT INTO capture_trace (`id`, `date`, `time_delta`, `direction`, `start_trigger_time`, `end_trigger_time`, `sample_count`, `min_sample`, `max_sample`, `tag`, `comments`) VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                    captureId, DATE, 10, -1, DATE, DATE, NUM_SAMPLES_PER_CAPTURE, 0, NUM_SAMPLES_PER_CAPTURE, "NORMAL", "SOME COMMENTS");

            for (int sampleId = 1; sampleId <= NUM_SAMPLES_PER_CAPTURE; sampleId++)
            {
                jdbcTemplate.update("INSERT INTO capture_trace_sample (`capture_trace`,  `sample_number`, `value`) VALUES(?,?,?)", captureId, sampleId, sampleId * 10);
            }

            if (captureId % 100 == 0)
            {
                System.out.println("Inserted " + captureId + "/" + NUM_CAPTURES + " captures");
            }
        }
    }
}
