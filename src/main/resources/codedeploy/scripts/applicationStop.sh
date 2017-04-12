#!/bin/bash
SERVICE=@project.artifactId@

if (( $(ps -ef | grep -v grep | grep $SERVICE | wc -l) > 0 )); then
  service $SERVICE stop
else
  echo "'$SERVICE' is already stopped"
fi
