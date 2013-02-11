#!/bin/bash

cd ./lib/collab-panel;
ant;
cd ./../..;
cd ./lib/collab-server;
ant;
cd ../..;
ant;
