import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerDemo {
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    // 这里可以配置几台broker即可，他会自动从broker去拉取元数据进行缓存
    props.put("bootstrap.servers", "node01:9092,node02:9092,node03:9092");
    // 这个就是负责把发送的key从字符串序列化为字节数组
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    // 这个就是负责把你发送的实际的message从字符串序列化为字节数组
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("acks", "0");
    //    props.put("batch.size", 323840);
    //    props.put("linger.ms", 10);

    props.put("producer.type","async");
    props.put("batch.size", "4194304");
    props.put("linger.ms", 10000);
    props.put("buffer.memory", 33554432);
    props.put("max.block.ms", 3000);
    // 创建一个Producer实例：线程资源，跟各个broker建立socket连接资源
    KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);

    long t1 = System.currentTimeMillis();
    for (int i = 0; i < 100000; i++) {
      ProducerRecord<String, String> record =
          new ProducerRecord<String, String>(
              "tbl_plugin_req_1", "test-key", getStr(new Random().nextInt(1000)));
      producer.send(record, new Callback() {
        public void onCompletion(RecordMetadata metadata, Exception exception) {

        }
      });

    }

    System.out.println(System.currentTimeMillis() - t1);

    producer.close();
  }

  private static String getStr(int length) {
    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(62);
      sb.append(str.charAt(number));
    }
    return sb.toString();
  }


}
