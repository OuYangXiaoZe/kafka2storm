package kafkaAndStorm;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

public class KafkaAndStormTopologyMain {
    public static void main(String[] args) throws Exception{
        TopologyBuilder topologyBuilder = new TopologyBuilder();
        topologyBuilder.setSpout("kafkaSpout",
                new KafkaSpout(new SpoutConfig(
                        new ZkHosts("master:2181,node1:2181,node2:2181"),
                        "hello",
                        "/mySpout",
                        "orderMq1")),1);
        topologyBuilder.setBolt("mybolt1",new MyBolt1(),1).shuffleGrouping("kafkaSpout");

        Config config = new Config();
//        config.setDebug(true);
        config.setNumWorkers(1);

        //3、提交任务  -----两种模式 本地模式和集群模式
        if (args.length>0) {
            StormSubmitter.submitTopology(args[0], config, topologyBuilder.createTopology());
        }else {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("storm2kafka", config, topologyBuilder.createTopology());
        }
    }
}
