Collab
==============

Collab is paintchat software. Visit http://collab.mgn.cz/ for more info.

License
-------

Collab desktop is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

Collab desktop is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.

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
 * add_copyright_to_all_files.sh - script using copyright-header tool (http://github.com/osterman/copyright-header) for adding copyright header to all source files 

