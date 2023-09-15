package com.lgypro.j8s.framework.plugins.queuesort;

import com.lgypro.j8s.framework.PodInfo;
import com.lgypro.j8s.framework.QueuedPodInfo;
import com.lgypro.j8s.scheduler.testing.Wrappers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

class PrioritySortTest {
    PrioritySort prioritySort = PrioritySort.newInstance(null, null);

    @ParameterizedTest
    @MethodSource("podInfoProvider")
    void testLess(String name, QueuedPodInfo p1, QueuedPodInfo p2, boolean expected) {
        Assertions.assertEquals(prioritySort.less(p1, p2), expected, name);
    }

    static Stream<Arguments> podInfoProvider() {
        int lowPriority = 10;
        int highPriority = 100;
        Instant time = Instant.now();
        Instant newerTime = Instant.now().plusSeconds(1);
        return Stream.of(
                Arguments.of("p1.priority less than p2.priority",
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(lowPriority).getPod())),
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(highPriority).getPod())),
                        false),
                Arguments.of(
                        "p1.priority greater than p2.priority",
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(highPriority).getPod())),
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(lowPriority).getPod())),
                        true),
                Arguments.of(
                        "equal priority. p1 is added to schedulingQ earlier than p2",
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(highPriority).getPod()), time),
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(highPriority).getPod()), newerTime),
                        true),
                Arguments.of(
                        "equal priority. p2 is added to schedulingQ earlier than p1",
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(highPriority).getPod()), newerTime),
                        new QueuedPodInfo(new PodInfo(Wrappers.makePod().setPriority(highPriority).getPod()), time),
                        false)
        );
    }
}