#!/bin/bash

cd ./lib/collab-panel;
ant;
cd ./lib/collab-server;
ant;
cd ../..;
ant;
