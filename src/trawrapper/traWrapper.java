

package trawrapper;

import anywheresoftware.b4a.AbsObjectWrapper;

import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BA.Author;
import anywheresoftware.b4a.BA.Hide;
import anywheresoftware.b4a.BA.Pixel;
import anywheresoftware.b4a.BA.ShortName;
import anywheresoftware.b4a.BA.Version;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.BA.DependsOn;
import anywheresoftware.b4a.keywords.Common.DesignerCustomView;
import anywheresoftware.b4a.objects.LabelWrapper;
import anywheresoftware.b4a.objects.PanelWrapper;
import anywheresoftware.b4a.objects.ViewWrapper;
import anywheresoftware.b4a.BA.ActivityObject;
import anywheresoftware.b4a.BA.Events;
import anywheresoftware.b4a.IOnActivityResult;
import anywheresoftware.b4a.BA.Permissions;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.graphics.Color;



import java.util.ArrayList;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.tuts.prakash.simpleocr.MainActivity;

@ActivityObject
@ShortName("OcrLib")
//@Events(values={"scan_result(image As Object)"})
//@Author("Github: Aditya Arora, Wrapped by: Johan Schoeman")
@Version(1.00f)
@DependsOn(values={"com.android.support:support-annotations", "com.android.support:support-v4", "com.android.support:appcompat-v7", "com.google.android.gms:play-services-vision"})
@Permissions(values={"android.permission.CAMERA"})

public class traWrapper extends AbsObjectWrapper<MainActivity> {

	
	@Hide
	public static BA ba;
	@Hide
	public static String eventName;
	private static MainActivity cv;
	private IOnActivityResult ion;
	private static final int RC_OCR_CAPTURE = 9003;
	
	Intent mIntent;
	

	
	public void Initialize(BA paramBA, String paramString) {
	    eventName = paramString.toLowerCase(BA.cul);
	    ba = paramBA;

	    MainActivity cv = new MainActivity();
	    String str = paramString.toLowerCase(BA.cul);
	
	    setObject(cv);
		mIntent = new Intent(BA.applicationContext, MainActivity.class);	
	
	}	


     public void startDetection() {
		ion = new IOnActivityResult() {
			
			@Override
			public void ResultArrived(int arg0, Intent data) {
				BA.Log("ResultArrived");
				if (data != null) {
                    String text = data.getStringExtra(MainActivity.mTextBlock);
                    //statusMessage.setText(R.string.ocr_success);
                    //textValue.setText(text);
                    BA.Log("Text read: " + text);
					  if (traWrapper.ba.subExists(traWrapper.eventName + "_scanned_text")) {
						traWrapper.ba.raiseEventFromDifferentThread(this, null, 0, traWrapper.eventName + "_scanned_text", true, new Object[] {text});
					  }					
					
                } else {
                    //statusMessage.setText(R.string.ocr_failure);
                    BA.Log("No Text captured, intent data is null");
                }
			}
		};
		ba.startActivityForResult(ion, mIntent);
	}
	

}
