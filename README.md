# common_utils
Common Utils for android

This library consists of some commonly used functions in android. It is still under development. I'll try my best to keep adding new stuffs.

Right now this has
* Material Colors
* FileUtils and StreamUtils
* CommonDialogs

<h2>Material Colors:</h2>
  You can use Google's material colors directly in your project.
  Like R.color.red_500, R.color.light_green_50 and so on.
  <br/>
<h2>FileUtils and StreamUtils:</h2>
  These consists of methods to read, write and copy files quickly.
  The documentation of those methods can be found under the /docs folder
  <br/>
<h2>CommonDialogs:</h2>
  This simplifies the process of creating some commmonly needed dialogs.
  There are builtin dialogs for showing info, getting text input(such as filenames), selecting options.
  This is another documented in /docs folder.
  <br/>
<br/>
<b>Note :</b>
  Currently I haven't given examples. But soon I'll add an example project to showcase how each methods can be used.
  As I said, this project is still under development. :)
  
  <h2>To use in your project</h2>

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        compile 'com.github.s-praveen-kumar:common_utils:-SNAPSHOT'
	}
