B_PATH=.
for i in ../lib/*.jar; do
LIB_PATH=${LIB_PATH}:$i
done

for i in ../serverLib/*.jar; do
LIB_PATH=${LIB_PATH}:$i
done

$JAVA_HOME/bin/java -server -Xmx128M -Xms128M -Xmn20M -XX:PermSize=500M -XX:MaxPermSize=500M -Xss256K -XX:ParallelGCThreads=8 -XX:+DisableExplicitGC -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=80 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:+PrintGC -Djava.awt.headless=true -classpath .:$CLASSPATH:$LIB_PATH:DataCaptureServer.jar com.wyd.rolequery.DataCaptureServer >> stdout.log 2>&1 &

echo $! > dddDataCaptureServer.pid
