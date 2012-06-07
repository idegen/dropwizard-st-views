#!/bin/sh

export ANT_OPTS='-Xmx1024m -Xms512m'
export ANT_HOME='tools/apache-ant-1.8.4'

tools/apache-ant-1.8.4/bin/ant -buildfile build.xml $*
