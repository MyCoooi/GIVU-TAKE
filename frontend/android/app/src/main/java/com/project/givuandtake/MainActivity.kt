
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GivuAndTakeTheme {
                val navController = rememberNavController() // NavController 생성
                var selectedItem by remember { mutableStateOf(0) } // 선택된 항목 상태 추가
                val cartItems = remember { mutableStateOf(listOf<CartItem>()) } // 장바구니 상태
                val currentBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = currentBackStackEntry?.destination?.route
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 화면 상단에 NavHost, 하단에 BottomNavBar 배치
                    Column {
                        NavHost(
                            navController = navController,
                            startDestination = "auth",
                            modifier = Modifier.weight(1f)
                        ) {
                            // 메인 페이지
                            composable("mainpage") { MainPage(navController) }
                            // 펀딩 페이지
                            composable("funding") { FundingMainPage(navController) }
                            // 펀딩 상세 페이지
                            composable(
                                "funding_detail/{title}/{location}/{startDate}/{endDate}/{nowAmount}/{goalAmount}/{imageUrl}"
                            ) { backStackEntry ->
                                val fundingCard = MainFundingCard(backStackEntry)
                                FundingDetailPage(
                                    fundingCard = fundingCard,
                                    navController = navController,
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                            composable("attraction") { AttractionMain(navController, "영도") } // Navigate to AttractionMain
                            // 로그인 페이지
                            composable("auth") { LoginScreen(navController) }
                            // 회원가입 페이지
                            composable("signup_step1") { SignupStep1(navController) }
                            composable("signup_step2") { SignupStep2(navController) }
                            composable("signup_step3") { SignupStep3(navController) }
                            // 기프트 페이지
                            composable("gift") {
                                GiftPage(navController = navController) // cartItems는 MutableState로 전달
                            }

                            // 기프트 상세 페이지
                            addGiftPageDetailRoute(navController, cartItems) // cartItems는 MutableState로 전달

                            // 장바구니 페이지
                            composable("cart_page") {
                                val context = LocalContext.current // LocalContext를 사용하여 Context 가져오기
                                CartPage(navController = navController, context = context) // context 전달
                            }

                            // 마이 페이지
                            composable("mypage") { ContributorScreen(navController) }
                            composable("locationSelection") {
                                LocationSelect(navController)
                            }
                            composable("attraction?city={city}") { backStackEntry ->
                                val city = backStackEntry.arguments?.getString("city") ?: "도 선택"
                                AttractionMain(navController, city)
                            }
                            composable(
                                route = "trippage?city={city}", // Define the route with a city argument
                                arguments = listOf(navArgument("city") { type = NavType.StringType }) // Declare argument type
                            ) { backStackEntry ->
                                // Retrieve the city from the arguments
                                val city = backStackEntry.arguments?.getString("city")
                                TripPage(navController, city) // Pass the city to TripPage
                            }
                        }

                        // 하단 네비게이션 바
                        if (currentDestination != "trippage?city={city}") {
                            BottomNavBar(navController, selectedItem) { newIndex ->
                                selectedItem = newIndex
                            }
                        }
                    }
                }
            }
        }
    }
}