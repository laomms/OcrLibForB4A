# OcrLibForB4A

original source:https://github.com/prakashpun/TextRecognitionAndroid 

![image](https://github.com/laomms/OcrLibForB4A/blob/main/cid2.gif)  

to use:

```vb

    #Extends: android.support.v7.app.AppCompatActivity
    #additionaljar:com.android.support:support-media-compat
    #AdditionalRes: ..\resource
    #AdditionalRes: ..\OCRres
    
Sub Globals
	  Dim avocr As OcrLib
End Sub

Sub Activity_Create(FirstTime As Boolean)
    '...   
    avocr.Initialize("avocr")
End Sub

Sub startDetection()
    avocr.startDetection
End Sub

Sub avocr_scanned_text(scannedText As String)
	Log(scannedText.Length)	
End Sub

```   
