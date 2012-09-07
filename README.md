Collab
==============

Collab is paintchat software. Visit http://collab.mgn.cz/ for more info.

Build
------

In Linux it's simple, just run build.sh script. In other systems wasn't build scripts created yet. You have to go to lib/collab-panel directory, there run ant after that go to project root and run ant here.

Builded class is now in ./build directory and genereted .jar is in ./deploy directory.

Other scripts
-------------

There are other useful scripts.

 * update.sh - pull greater version from git server and updates submodules
 * build.sh - build all (with submodules), (it's make .jar files)
 * run.sh - run Collab
 * build-run.sh - build then run Collab
 * update-build-run.sh - update collab, then build it, then start it

