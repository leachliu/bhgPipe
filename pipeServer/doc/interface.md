#pipeServer接口

##1.schemas
    {
        "id":"9",
        "name":"ITEM ",
        "fields":[
            {
                "name":"colA",
                "type":"int"
            },
            {
                "name":"colC",
                "type":"date"
            }
        ],
    }

##2.sites
    {
        "siteId":"1234",
        "siteName":"MOM",
        "createAt":"1496418015",
        "partitions":1,
        "server":{
        },
        "receiver":{
            "schemas":[
                "ITEM",
                "ORDER"
            ],
            "consumer":{
                "group.id":"groupId",
                "zookeeper.connect":"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183",
                "zookeeper.session.timeout.ms":"400",
                "zookeeper.sync.time.ms":"200",
                "auto.commit.interval.ms":"1000"
            }
        },
        "sender":{
            "groupId":1,
            "uri":"",
            "topic":"ITEM",
            "producer":{
                "metadata.broker.list":"127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183",
                "request.required.acks":"0",
                "producer.type":"async",
                "serializer.class":"kafka.serializer.StringEncoder",
                "partitioner.class":"JavaKafkaProducerPartitioner",
                "message.send.max.retries":"3",
                "batch.num.messages":"200",
                "send.buffer.bytes":"102400"
            }
        }
    }

##3.routings
    {
        "schema":"ITEM",
        "pattern":"ALL"
    }


