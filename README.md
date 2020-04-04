# GdtANE_Android

GDT_SDK for flash air android
( require air sdk32 )

1. 	install Android Studio :)
2. 	create a new Andoird project, select a Empty Activity
	2.1 change project type to lib ( this part is copy from myflashlabs.com)

		//apply plugin: 'com.android.application'
		apply plugin: 'com.android.library'
 
			android {
			    compileSdkVersion 22
			    buildToolsVersion "22.0.1"
	 
		    defaultConfig {
		        //applicationId "com.doitflash.myFirstANE"
		        minSdkVersion 10
		        targetSdkVersion 22
		        versionCode 1
		        versionName "1.0"
		    }
		    buildTypes {
		        release {
		            minifyEnabled false
		            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
		        }
		    }
		}
 
		dependencies {
		    compile fileTree(dir: 'libs', include: ['*.jar'])
		    compile 'com.android.support:appcompat-v7:22.1.1'
		    compile files('libs/FlashRuntimeExtensions.jar')
		}

	As you see, we have commented out //applicationId “com.doitflash.myFirstANE” and have replaced ‘com.android.application’ to ‘com.android.library’

3.	(optional) 
	For net reason, download the latetest gradle file ( ex. gradle-5.6.4-all.zip ) into {project_root}/gradle/wrapper.
	Edit the "gradle-wrapper.properties" file, change "distributionUrl" value to the local gradle file.

4. Import third libs.

	4.1 Create a directory named "libs" in {project_root}/app/
	4.2 Copy the third lib ( ex. gtdSDK.acc ) into "libs"
	4.3 In Android Studio, Create a new Module --> Import a .JAR/.AAR Package , then select the "third lib" from "4.2"

	4.4 Find "FlashRuntimeExtensions.jar" from Flex SDK into "libs"
	4.5 Right click the "FlashRuntimeExtensions.jar" File in the AndroidStudio, select "Add As Library"
	4.6 check build.gradle file in android studio : at the bottom 

		compile files('libs/FlashRuntimeExtensions.jar')

5. Code Native class.

	5.1 Create "Extension" ( or any names ) implements "FREExtension", which is the native entry.

			import com.adobe.fre.FREExtension;
			public class Extension  implements FREExtension {
			    public static ExContext context;
			    @Override
			    public ExContext createContext(String arg0)
			    {
			        context = new ExContext();
			        return context;
			    }
			    @Override
			    public void dispose()
			    {
			        context = null;
			    }
			    @Override
			    public void initialize() { }
			}

	5.2 Create "FunctionClasses" ( or any names ) implements "FREFunction", which is the exact call function entry. 

			public class myFirstFunClass implements FREFunction {
				public FREObject call(FREContext context, FREObject[] args) {
					// Try to process the call
					try {
						// Get The Extension Context
						ExContext cnt      = (ExContext) context;
						String msg1_string = args[0].getAsString();
						boolean msg2_bool  = args[1].getAsBool();
						cnt.dosometing(msg1_string); // do something in the ExContext
						return FREObject.newObject(true); // return value to the as3
					} catch (Exception e) {
						// Print the exception stack trace
						e.printStackTrace();
					}
					// Return
					return null;
				}
			}

	5.3 Map native functions in "getFunctions", in which register "FunctionClasses" you need.

			public class ExContext extends FREContext {
			    @Override
			    public Map<String, FREFunction> getFunctions()
			    {
			    	Map<String, FREFunction> functions = new HashMap<String, FREFunction>();
	        		// sometime, register native functions by the same name in diff ane libs may cause strange problem
	        		String preFunName = "preFix"; 
	        		functions.put("firstFunName", new myFirstFunClass());
	        		return functions;
	    		 }
    		}

6. Create swc lib in flashbuilder.

	6.1 Create context by "ExtensionContext.createExtensionContext(ane_id, '')" in the as file.
	6.2 Call native functions by "names" ( registered in "5.3" )

			package com.xxx.ane_swc
			{
				import flash.utils.getDefinitionByName;			
				public class ANE
				{
					private static function create():void
					{
						if(created)return;
						try
						{
							var cls:Class = getDefinitionByName("flash.external.ExtensionContext") as Class;
							if(cls)
							{
								var Your_ANE_id:String = "example.ane.aneID";
								_context = cls['createExtensionContext'](Your_ANE_id, ''); 
								_call = _context.call;
							}
						}
						catch(er:Error)
						{
						}
						created = true;
					}
					private static var created:Boolean;
					private static var _context:Object;
					private static var _call:Function;
					//ex. call( "firstFunName", "msg1", true )
					public static function get call():Function
					{
						// you can call the native funcion, ex. "firstFunName", then the argument
						//  _context.call( "firstFunName", "msg1", true )
						create();
						return _call;
					}
					public static function get context():*
					{
						create();
						return _context;
					}
					public static function dispose():void
					{
						if(_context)
						{
							_context.dispose();
							_context = null;
						}
					}
				}
			}

7. Build the ANE File

	7.1 create a new directory named "shell"( or any other names)
	7.2 Build the swc lib in the FlashBuilder ( in step 6), and get the .swc file, copy into "shell".
		unzip .swc get the library.swf, copy into "shell"

	7.3 Copy the "res" from third libs into the "shell" directory.
    7.4 Rebuild the java project in Android Studio.
    	Then find the acc File in the {project_root}/app/build/outputs/aar
    	Unzip it and Copy "classes.jar" into "shell" directory, rename this .jar to "myClass1.jar"
    7.5 Copy all the .jar you need except "FlashRuntimeExtensions.jar" into "shell".
    7.6 Create "extension.xml" in "shell"

    	<extension xmlns="http://ns.adobe.com/air/extension/32.0">
		    <id>example.ane.aneID</id> <!-- Your ANE id will be used in the as3 -->
		    <versionNumber>1</versionNumber>
		    <platforms>
		        <platform name="Android-ARM">
		            <applicationDeployment>
		                <nativeLibrary>myClass1.jar</nativeLibrary>
		                <initializer>com.xxx.xxxx.Extension</initializer>  <!--package to the Extension.java -->
		            </applicationDeployment>
		        </platform>
		    </platforms>
		</extension>

	7.7 Create "platform.xml" in "shell"

		<platform xmlns="http://ns.adobe.com/air/extension/32.0">
			<packagedDependencies>
		    <!-- <packagedDependency>android-support-v4.jar</packagedDependency> --> 
		    <!-- <packagedDependency>android-support-v7.jar</packagedDependency> -->
		    <!-- <packagedDependency>constraint-layout.jar</packagedDependency> -->
			<packagedDependency>android-query-full.0.26.7.jar</packagedDependency> 
			<packagedDependency>GDTSDK.unionNormal.4.176.1046.jar</packagedDependency> 
		</packagedDependencies> 
		<packagedResources>
			<packagedResource>
				<packageName>com.qq.e</packageName><!-- the package name of the third lib -->
		    <folderName>res</folderName><!-- the res folder of the third lib -->
		</packagedResource>
		</packagedResources>
		</platform>


	I didn't build the android-support-v4.jar, and android-support-v7.jar because some greate guy has done it for us.
	You can get the basic support from https://github.com/tuarua/Android-ANE-Dependencies/tree/master/anes/support

	7.8 Create or copy .p12 file in "shell"

	7.9 build .ANE

	"{path_to_air_sdk}\AIRSDK\bin\adt" -package -tsa none -storetype PKCS12 -keystore key.p12 -storepass 000000 -target ane myFirst.ane extension.xml -swc *.swc -platform Android-ARM -platformoptions platform.xml library.swf *.jar .\res   


8. then... import the ane into your flash project.
	Don't forget , Copy some asset from the third sdk dic into {as_project_root}


	and enjoy your debug time.


