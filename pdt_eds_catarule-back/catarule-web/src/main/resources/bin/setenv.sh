# Local address default as below, if you have more net-interface, you must specify one
LOCAL_ADDRESS=`/sbin/ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
#echo LOCAL_ADDRESS:$LOCAL_ADDRESS

# Solves the problem of multiple NIC
LOCAL_ADDRESS=`echo $LOCAL_ADDRESS | awk '{print $1}'`
#echo LOCAL_ADDRESS RANDOM:$LOCAL_ADDRESS

JAVA_OPTS="$JAVA_OPTS -server"
JAVA_OPTS="$JAVA_OPTS -Xms8g -Xmx8g -Xss512K"
JAVA_OPTS="$JAVA_OPTS -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:CompressedClassSpaceSize=64m -XX:+AlwaysPreTouch"
JAVA_OPTS="$JAVA_OPTS -XX:ReservedCodeCacheSize=128m -XX:InitialCodeCacheSize=128m -XX:+UseG1GC -XX:G1HeapRegionSize=4M"
#JAVA_OPTS="$JAVA_OPTS -Xss256k -XX:NewSize=2g -XX:MaxNewSize=2g -XX:SurvivorRatio=22 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m"
#JAVA_OPTS="$JAVA_OPTS -XX:+UseParNewGC -XX:ParallelGCThreads=4 -XX:MaxTenuringThreshold=9 -XX:+UseConcMarkSweepGC"
#JAVA_OPTS="$JAVA_OPTS -XX:CMSInitiatingOccupancyFraction=60  -XX:+UseCMSInitiatingOccupancyOnly"
#JAVA_OPTS="$JAVA_OPTS -XX:+ScavengeBeforeFullGC -XX:+CMSParallelRemarkEnabled"
#JAVA_OPTS="$JAVA_OPTS -XX:+CMSClassUnloadingEnabled -XX:SoftRefLRUPolicyMSPerMB=0"
#JAVA_OPTS="$JAVA_OPTS -XX:-ReduceInitialCardMarks -XX:+CMSPermGenSweepingEnabled -XX:+ExplicitGCInvokesConcurrent"
JAVA_OPTS="$JAVA_OPTS -Dclient.encoding.override=UTF-8 -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"
JAVA_OPTS="$JAVA_OPTS -Duser.region=CN -Duser.timezone=GMT+08"

#JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=15150"
REMOTE_DEBUG_PORT=`shuf -i 30000-60000 -n 1`
#JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=$REMOTE_DEBUG_PORT"

#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote"
#JAVA_OPTS="$JAVA_OPTS -Djava.rmi.server.hostname=192.168.1.34"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.port=2018"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.rmi.port=2018"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.ssl=false"
#JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.authenticate=false"

#JAVA_OPTS="$JAVA_OPTS -Dserver.address=$LOCAL_ADDRESS"

# which spring profile will be actived
PROFILE="prod"