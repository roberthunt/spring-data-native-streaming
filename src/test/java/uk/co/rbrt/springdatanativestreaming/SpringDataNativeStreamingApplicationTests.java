package uk.co.rbrt.springdatanativestreaming;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataNativeStreamingApplicationTests {

	@Autowired
	CaptureTraceRepository repository;

	@Test
    @Transactional(readOnly = true)
	public void queryTime()
	{
		final long st = System.nanoTime();
		try (Stream<Object[]> stream = repository.findStream())
        {
            // nop
        }
		System.out.println("##### Total time: " + Duration.ofNanos(System.currentTimeMillis() - st).toMillis() + "ms");
	}

    @Test
    @Transactional(readOnly = true)
    public void queryTimeAndConsumption()
    {
        final long[] assemblyTime = {0};
        final long st = System.nanoTime();

        final Map<BigInteger, CaptureTrace> captures = new HashMap<>();

        try (Stream<Object[]> stream = repository.findStream())
        {
            stream.forEach(objects -> {
                final long assemblyStart = System.nanoTime();
                CaptureTrace captureTrace = captures.get(objects[0]);

                if (captureTrace == null)
                {
                    captureTrace = new CaptureTrace();
                    captureTrace.setId( ((BigInteger) objects[0]).longValue());
                    captureTrace.setDate( ((BigInteger) objects[1]).longValue() );
                    captureTrace.setComments( (String) objects[2] );
                    captureTrace.setDirection( (Integer) objects[3] );
                    captureTrace.setStartTriggerTime( ((BigInteger) objects[4]).longValue() );
                    captureTrace.setEndTriggerTime( ((BigInteger) objects[5]).longValue() );
                    captureTrace.setMaxSample( (Double) objects[6] );
                    captureTrace.setMinSample( (Double) objects[7] );
                    captureTrace.setSampleCount( (Integer) objects[8] );
                    captureTrace.setTag( CaptureTraceTag.valueOf((String) objects[9]) );
                    captureTrace.setTimeDelta( (Double) objects[10] );

                    captures.put((BigInteger) objects[0], captureTrace);
                }

                captureTrace.getSamples().add(new CaptureTraceSample( ((Short) objects[11]), ((Double) objects[12])) );
                assemblyTime[0] += System.nanoTime() - assemblyStart;
            });
        }

        System.out.println("#### Assembly time: " + Duration.ofNanos(assemblyTime[0]).toMillis() + "ms");
        System.out.println("#### Total time: " + Duration.ofNanos(System.nanoTime() - st).toMillis() + "ms");
    }

}
