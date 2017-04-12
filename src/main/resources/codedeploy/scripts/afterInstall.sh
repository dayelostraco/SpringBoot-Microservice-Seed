#!/bin/bash

DIR=/var/@project.artifactId@
APPNAME=@project.artifactId@

chown codedeploy:codedeploy "$DIR/$APPNAME.jar"
chmod 500 "$DIR/$APPNAME.jar"
sudo chattr +i "$FULLDIR/$APPNAME.jar"

chown codedeploy:codedeploy "$DIR/$APPNAME.conf"
chmod 500 "$DIR/$APPNAME.conf"

ln -s "$DIR/$APPNAME.jar" /etc/init.d/$APPNAME

chkconfig $APPNAME on
