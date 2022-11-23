#!/usr/bin/env sh
OPTS="-XX:G1HeapRegionSize=16MB -XX:-UseContainerSupport -server -XX:+UseStringDeduplication -XX:+UseG1GC -XX:+DisableExplicitGC -XX:-HeapDumpOnOutOfMemoryError -XX:+AggressiveOpts -XX:+PrintCompilation"
if [ -n "$JAVA_OPTS" ];then
  JAVA_OPTS="$OPTS $JAVA_OPTS"
else
  JAVA_OPTS="-Xms256m -Xmx512m -Xss1m $OPTS"
fi

java $JAVA_PRE_OPTS org.springframework.boot.loader.JarLauncher $JAVA_OPTS
