package com.project.givuandtake.feature.mypage.MyActivities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.project.givuandtake.R
import java.io.File

var imageCapture: ImageCapture? = null

fun capturePhoto(context: Context, onImageCaptured: (Bitmap) -> Unit) {
    val photoFile = File(context.filesDir, "card_image.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture?.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                onImageCaptured(bitmap)  // 촬영된 이미지를 전달
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraX", "Image capture failed", exception)
            }
        }
    )
}

@Composable
fun RequestCameraPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasPermission = granted
            if (granted) {
                onPermissionGranted()
            }
        }
    )

    LaunchedEffect(hasPermission) {
        if (!hasPermission) {
            launcher.launch(Manifest.permission.CAMERA)
        } else {
            onPermissionGranted()
        }
    }
}

//fun capturePhoto(onImageCaptured: (Bitmap) -> Unit) {
//    val photoFile = File(context.filesDir, "card_image.jpg")
//    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//    imageCapture.takePicture(
//        outputOptions,
//        ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
//                val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
//
//                // 여기서 이미지를 크롭
//                val croppedBitmap = cropToCardArea(bitmap)
//
//                // 크롭된 이미지를 넘겨줌
//                onImageCaptured(croppedBitmap)
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                Log.e("CameraX", "Image capture failed", exception)
//            }
//        }
//    )
//}

fun cropToCardArea(bitmap: Bitmap): Bitmap {
    // 카드 영역 크롭
    val left = 50  // 임의의 좌표
    val top = 100
    val width = 300
    val height = 180

    return Bitmap.createBitmap(bitmap, left, top, width, height)
}

@Composable
fun CameraPreview(modifier: Modifier = Modifier, onCameraControlReady: (androidx.camera.core.CameraControl) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    LaunchedEffect(Unit) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProvider = cameraProviderFuture.get()
    }

    cameraProvider?.let { provider ->
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                imageCapture = ImageCapture.Builder().build()  // 이미지 캡처 초기화

                try {
                    provider.unbindAll()
                    val camera = provider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageCapture  // 이미지 캡처 사용
                    )
                    onCameraControlReady(camera.cameraControl)
                } catch (e: Exception) {
                    Log.e("CameraX", "Use case binding failed", e)
                }

                previewView
            },
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun CardRegistration(navController: NavController) {
    var hasCameraPermission by remember { mutableStateOf<Boolean?>(null) }
    var isFlashOn by remember { mutableStateOf(false) }
    var cameraControl by remember { mutableStateOf<androidx.camera.core.CameraControl?>(null) }
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) } // 촬영된 이미지를 저장할 상태

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            hasCameraPermission = true
        } else {
            launcher.launch(Manifest.permission.CAMERA)
        }
    }

    when (hasCameraPermission) {
        null -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }
        true -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    onCameraControlReady = { control ->
                        cameraControl = control
                    }
                )

                // 카드 영역과 다른 UI
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable { navController.popBackStack() }
                    )
                    Spacer(modifier = Modifier.height(150.dp))

                    // 카드 모양의 네모
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 300.dp, height = 180.dp)
                                .border(
                                    BorderStroke(
                                        3.dp, // 보더의 두께
                                        brush = Brush.linearGradient(
                                            colors = listOf(Color(0xFFA093DE), Color.White),  // 보더 색상을 흰색과 검정색으로
                                            tileMode = TileMode.Repeated  // 반복 모드로 설정
                                        )
                                    ),
                                    shape = RoundedCornerShape(15.dp)  // 둥근 모서리 적용
                                )
                                .background(Color.Transparent)  // 카드 박스는 투명
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "사각형 안에 카드를 맞추고 촬영해주세요",
                            fontSize = 16.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        if (capturedImage == null) {

                        } else {
                            Image(
                                bitmap = capturedImage!!.asImageBitmap(),
                                contentDescription = "Captured Image",
                                modifier = Modifier
                                    .size(300.dp)  // 이미지 크기 설정
                                    .clip(RoundedCornerShape(15.dp))  // 둥근 모서리
                                    .border(BorderStroke(3.dp, Color.Gray))  // 테두리 설정
                            )
                            Button(
                                onClick = {
                                    capturedImage = null  // 다시 촬영하도록 설정
//                                    croppedImage = null  // 크롭된 이미지도 초기화
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD6D6D6)),
                                shape = RoundedCornerShape(50),
                                modifier = Modifier.size(56.dp)
                            ) {
                                Text(text = "다시 촬영", fontSize = 16.sp, color = Color.Black)
                            }

                        }

//                        if (capturedImage == null) {
//                            // 아직 촬영된 이미지가 없는 경우
//                            Button(
//                                onClick = {
//                                    // 사진을 촬영하고 크롭된 이미지를 보여줌
//                                    capturePhoto { bitmap ->
//                                        capturedImage = bitmap
//                                    }
//                                },
//                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD6D6D6)),
//                                shape = RoundedCornerShape(50),
//                                modifier = Modifier.size(56.dp)
//                            ) {
//                                Icon(
//                                    painter = painterResource(id = R.drawable.baseline_camera_24),
//                                    contentDescription = "Toggle Flash",
//                                    tint = if (isFlashOn) Color.Yellow else Color(0xFFA093DE),
//                                    modifier = Modifier.size(20.dp)
//                            }
//
//                        } else {
//                        // 촬영된 이미지를 보여줌
//                            Image(
//                                bitmap = capturedImage!!.asImageBitmap(),
//                                contentDescription = "Captured Image",
//                                modifier = Modifier
//                                    .size(300.dp) // 원하는 크기로 조정
//                                    .clip(RoundedCornerShape(15.dp)) // 모양 설정
//                                    .border(BorderStroke(2.dp, Color.Black))
//                                )
//                        }

                        Spacer(modifier = Modifier.height(50.dp))
                        Button(
                            onClick = {
                                capturePhoto(context) { bitmap ->
                                    capturedImage = bitmap  // 촬영된 이미지를 저장
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD6D6D6)),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_camera_24),
                                contentDescription = "Toggle Flash",
                                tint = if (isFlashOn) Color.Yellow else Color(0xFFA093DE),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(50.dp))

                        Button(
                            onClick = { navController.navigate("cardcustomregistration") },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA093DE)),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 64.dp)
                                .height(48.dp)
                        ) {
                            Text(
                                text = "수동으로 카드 입력",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.height(150.dp))

                        Button(
                            onClick = {
                                isFlashOn = !isFlashOn
                                cameraControl?.enableTorch(isFlashOn) // 플래시 토글
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFD6D6D6)),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier.size(56.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_flashlight_on_24),
                                contentDescription = "Toggle Flash",
                                tint = if (isFlashOn) Color.Yellow else Color(0xFFA093DE),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
        false -> {
            navController.navigate("cardcustomregistration")
        }
    }
}


