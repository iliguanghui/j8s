package com.lgypro.j8s.framework.plugins.imagelocality;

import com.lgypro.j8s.api.core.v1.*;
import com.lgypro.j8s.framework.NodeScore;
import com.lgypro.j8s.framework.ScorePlugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static com.lgypro.j8s.framework.plugins.imagelocality.ImageLocality.mb;

class ImageLocalityTest {
    ImageLocality imageLocality = ImageLocality.newInstance(null, null);

    @Test
    void testImageLocalityPriority() {
        PodSpec podSpec40250 = PodSpec.builder().setContainers(List.of(
                Container.builder().setImage("gcr.io/40").build(),
                Container.builder().setImage("gcr.io/250").build()
        )).build();
        PodSpec podSpec40300 = PodSpec.builder().setContainers(List.of(
                Container.builder().setImage("gcr.io/40").build(),
                Container.builder().setImage("gcr.io/300").build()
        )).build();
        PodSpec podSpec104000 = PodSpec.builder().setContainers(List.of(
                Container.builder().setImage("gcr.io/10").build(),
                Container.builder().setImage("gcr.io/4000").build()
        )).build();
        PodSpec podSpec300600900 = PodSpec.builder().setContainers(List.of(
                Container.builder().setImage("gcr.io/300").build(),
                Container.builder().setImage("gcr.io/600").build(),
                Container.builder().setImage("gcr.io/900").build()
        )).build();
        PodSpec podSpec3040 = PodSpec.builder().setContainers(List.of(
                Container.builder().setImage("gcr.io/30").build(),
                Container.builder().setImage("gcr.io/40").build()
        )).build();


        NodeStatus nodeStatus403002000 = NodeStatus.builder().setImages(
                List.of(
                        new ContainerImage(List.of(
                                "gcr.io/40:latest",
                                "gcr.io/40:v1",
                                "gcr.io/40:v1"
                        ), 40 * mb),
                        new ContainerImage(List.of(
                                "gcr.io/300:latest",
                                "gcr.io/300:v1"
                        ), 300 * mb),
                        new ContainerImage(List.of(
                                "gcr.io/2000:latest"
                        ), 2000 * mb)
                )
        ).build();
        NodeStatus nodeStatus25010 = NodeStatus.builder().setImages(
                List.of(
                        new ContainerImage(List.of(
                                "gcr.io/250:latest"
                        ), 250 * mb),
                        new ContainerImage(List.of(
                                "gcr.io/10:latest",
                                "gcr.io/10:v1"
                        ), 10 * mb)
                )
        ).build();

        NodeStatus nodeStatus60040900 = NodeStatus.builder().setImages(List.of(
                new ContainerImage(List.of(
                        "gcr.io/600:latest"
                ), 600 * mb),
                new ContainerImage(List.of(
                        "gcr.io/40:latest"
                ), 40 * mb),
                new ContainerImage(List.of(
                        "gcr.io/900:latest"
                ), 900 * mb)
        )).build();

        NodeStatus nodeStatus300600900 = NodeStatus.builder().setImages(List.of(
                new ContainerImage(List.of(
                        "gcr.io/300:latest"
                ), 300 * mb),
                new ContainerImage(List.of(
                        "gcr.io/600:latest"
                ), 600 * mb),
                new ContainerImage(List.of(
                        "gcr.io/900:latest"
                ), 900 * mb)
        )).build();

        NodeStatus nodeStatus400030 = NodeStatus.builder().setImages(List.of(
                new ContainerImage(List.of(
                        "gcr.io/4000:latest"
                ), 4000 * mb),
                new ContainerImage(List.of(
                        "gcr.io/30:latest"
                ), 30 * mb)
        )).build();

        NodeStatus nodeStatus203040 = NodeStatus.builder().setImages(List.of(
                new ContainerImage(List.of(
                        "gcr.io/20:latest"
                ), 20 * mb),
                new ContainerImage(List.of(
                        "gcr.io/30:latest"
                ), 30 * mb),
                new ContainerImage(List.of(
                        "gcr.io/40:latest"
                ), 40 * mb)
        )).build();

        NodeStatus nodeWithNoImages = NodeStatus.builder().setImages(Collections.emptyList()).build();
        class TestCase {
            final Pod pod;
            final List<Node> nodes;
            final String name;
            final List<NodeScore> expectedList;

            public TestCase(Pod pod, List<Node> nodes, String name, List<NodeScore> expectedList) {
                this.pod = pod;
                this.nodes = nodes;
                this.name = name;
                this.expectedList = expectedList;
            }
        }
        List<TestCase> testCases = List.of(
                /*
                Pod: gcr.io/40 gcr.io/250
                Node1
                Image: gcr.io/40:latest 40MB
                Score: 0 (40M/2 < 23M, min-threshold)

                Node2
                Image: gcr.io/250:latest 250MB
                Score: 100 * (250M/2 - 23M)/(1000M * 2 - 23M) = 5
                 */
                new TestCase(Pod.builder().setSpec(podSpec40250).build(),
                        List.of(
                                makeImageNode("node1", nodeStatus403002000),
                                makeImageNode("node2", nodeStatus25010)
                        ),
                        "two images spread on two nodes, prefer the larger image one",
                        List.of(
                                new NodeScore("node1", 0),
                                new NodeScore("node2", 5)
                        )),
                /*
                // Pod: gcr.io/40 gcr.io/300
                // Node1
                // Image: gcr.io/40:latest 40MB, gcr.io/300:latest 300MB
                // Score: 100 * ((40M + 300M)/2 - 23M)/(1000M * 2 - 23M) = 7
                // Node2
                // Image: not present
                // Score: 0
                 */
                new TestCase(Pod.builder().setSpec(podSpec40300).build(),
                        List.of(
                                makeImageNode("node1", nodeStatus403002000),
                                makeImageNode("node2", nodeStatus25010)
                        ),
                        "two images on one node, prefer this node",
                        List.of(
                                new NodeScore("node1", 7),
                                new NodeScore("node2", 0)
                        )),
                /*
            // Pod: gcr.io/4000 gcr.io/10

			// Node1
			// Image: gcr.io/4000:latest 2000MB
			// Score: 100 (4000 * 1/2 >= 1000M * 2, max-threshold)

			// Node2
			// Image: gcr.io/10:latest 10MB
			// Score: 0 (10M/2 < 23M, min-threshold)
                 */
                new TestCase(Pod.builder().setSpec(podSpec104000).build(),
                        List.of(
                                makeImageNode("node1", nodeStatus400030),
                                makeImageNode("node2", nodeStatus25010)
                        ),
                        "if exceed limit, use limit",
                        List.of(
                                new NodeScore("node1", (int) ScorePlugin.maxNodeScore),
                                new NodeScore("node2", 0)
                        )
                ),
                /*
                // Pod: gcr.io/4000 gcr.io/10
                // Node1
                // Image: gcr.io/4000:latest 4000MB
                // Score: 100 * (4000M/3 - 23M)/(1000M * 2 - 23M) = 66

                // Node2
                // Image: gcr.io/10:latest 10MB
                // Score: 0 (10M*1/3 < 23M, min-threshold)

                // Node3
                // Image:
                // Score: 0
                 */
                new TestCase(Pod.builder().setSpec(podSpec104000).build(),
                        List.of(
                                makeImageNode("node1", nodeStatus400030),
                                makeImageNode("node2", nodeStatus25010),
                                makeImageNode("node3", nodeWithNoImages)
                        ),
                        "if exceed limit, use limit (with node which has no images present)",
                        List.of(
                                new NodeScore("node1", 66),
                                new NodeScore("node2", 0),
                                new NodeScore("node2", 0)
                        )),
                /*
                // Pod: gcr.io/300 gcr.io/600 gcr.io/900

                // Node1
                // Image: gcr.io/600:latest 600MB, gcr.io/900:latest 900MB
                // Score: 100 * (600M * 2/3 + 900M * 2/3 - 23M) / (1000M * 3 - 23M) = 32

                // Node2
                // Image: gcr.io/300:latest 300MB, gcr.io/600:latest 600MB, gcr.io/900:latest 900MB
                // Score: 100 * (300M * 1/3 + 600M * 2/3 + 900M * 2/3 - 23M) / (1000M *3 - 23M) = 36

                // Node3
                // Image:
                // Score: 0
                 */
                new TestCase(
                        Pod.builder().setSpec(podSpec300600900).build(),
                        List.of(
                                makeImageNode("node1", nodeStatus60040900),
                                makeImageNode("node2", nodeStatus300600900),
                                makeImageNode("node3", nodeWithNoImages)
                        ),
                        "pods with multiple large images, node2 is preferred",
                        List.of(
                                new NodeScore("node1", 32),
                                new NodeScore("node2", 36),
                                new NodeScore("node3", 0)
                        )
                ),
                /*
                		{
			// Pod: gcr.io/30 gcr.io/40

			// Node1
			// Image: gcr.io/20:latest 20MB, gcr.io/30:latest 30MB gcr.io/40:latest 40MB
			// Score: 100 * (30M + 40M * 1/2 - 23M) / (1000M * 2 - 23M) = 1

			// Node2
			// Image: 100 * (30M - 23M) / (1000M * 2 - 23M) = 0
			// Score: 0
			pod:          &v1.Pod{Spec: test3040},
			nodes:        []*v1.Node{makeImageNode("node1", node203040), makeImageNode("node2", node400030)},
			expectedList: []framework.NodeScore{{Name: "node1", Score: 1}, {Name: "node2", Score: 0}},
			name:         "pod with multiple small images",
		},
                 */
                new TestCase(
                        Pod.builder().setSpec(podSpec3040).build(),
                        List.of(
                                makeImageNode("node1", nodeStatus203040),
                                makeImageNode("node2", nodeStatus400030)
                        ),
                        "pod with multiple small images",
                        List.of(
                                new NodeScore("node1", 1),
                                new NodeScore("node2", 0)
                        )
                )
        );
        // TODO: 跑完测试
        for (TestCase testCase : testCases) {
            List<Node> nodes = testCase.nodes;
            List<NodeScore> gotList = new ArrayList<>();
            for (Node node : nodes) {
                String nodeName = node.getObjectMeta().getName();
            }
        }
    }


    @ParameterizedTest
    @MethodSource
    void testNormalizedImageName(String description, String input, String expected) {
        String output = ImageLocality.normalizeImageName(input);
        Assertions.assertEquals(expected, output);
    }

    static List<Arguments> testNormalizedImageName() throws NoSuchAlgorithmException {
        return List.of(
                Arguments.of("add :latest postfix 1", "root", "root:latest"),
                Arguments.of("add :latest postfix 2", "gcr.io:5000/root", "gcr.io:5000/root:latest"),
                Arguments.of("keep it as is 1", "root:tag", "root:tag"),
                Arguments.of("keep it as is 2", "root@sha256:4813494d137e1631bba301d5acab6e7bb7aa74ce1185d456565ef51d737677b2", "root@" + getImageFakeDigest("root"))
        );
    }


    static Node makeImageNode(String name, NodeStatus status) {
        Node node = new Node(name);
        node.setStatus(status);
        return node;
    }

    static String getImageFakeDigest(String fakeContent) throws NoSuchAlgorithmException {
        byte[] hash = sha256(fakeContent);
        return "sha256:" + bytesToHex(hash);
    }

    static byte[] sha256(String content) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(content.getBytes(StandardCharsets.UTF_8));
        return digest.digest();
    }

    static String bytesToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte datum : data) {
            String hex = Integer.toHexString(datum);
            if (hex.length() == 1) {
                sb.append("0");
            } else if (hex.length() == 8) {
                hex = hex.substring(6);
            }
            sb.append(hex);
        }
        return sb.toString().toLowerCase(Locale.getDefault());
    }
}