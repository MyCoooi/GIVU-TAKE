package com.project.givuandtake.feature.gift





//@Composable
//fun FavoriteListPage(navController: NavController, favoriteItems: List<GiftDetail>) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // 상단 타이틀과 장바구니 아이콘
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = { navController.popBackStack() }) {
//                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
//            }
//            Text(text = "찜 목록", fontWeight = FontWeight.Bold, fontSize = 20.sp)
//            CartIcon(cartItemCount = favoriteItems.size, onCartClick = {
//                navController.navigate("cart_page")
//            })
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // 총 찜한 개수 표시
//        Text(text = "총 ${favoriteItems.size}개", modifier = Modifier.padding(8.dp), color = Color.Blue)
//
//        // 찜한 상품 리스트
//        LazyColumn(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(favoriteItems) { item ->
//                FavoriteItemCard(item = item, onDeleteClick = { /* TODO: 삭제 처리 */ }, onCartClick = { /* TODO: 장바구니 처리 */ })
//            }
//        }
//    }
//}
//
//@Composable
//fun FavoriteItemCard(
//    item: GiftDetail,
//    onDeleteClick: () -> Unit,
//    onCartClick: () -> Unit
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//            .background(Color.White)
//            .border(1.dp, Color.Gray.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        // 상품 이미지와 정보
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.weight(1f)
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(64.dp)
//                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
//            )
//            Spacer(modifier = Modifier.width(16.dp))
//            Column {
//                Text(text = item.name, fontWeight = FontWeight.Bold)
//                Text(text = "${item.price}원", color = Color.Gray)
//            }
//        }
//
//        // 삭제 및 장바구니 담기 버튼
//        Row {
//            Button(
//                onClick = onDeleteClick,
//                modifier = Modifier.padding(end = 8.dp),
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE0E0E0))
//            ) {
//                Text(text = "삭제", color = Color.Black)
//            }
//            Button(
//                onClick = onCartClick,
//                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE8E1FF))
//            ) {
//                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
//                Spacer(modifier = Modifier.width(4.dp))
//                Text(text = "담기", color = Color.Black)
//            }
//        }
//    }
//}
//
//@Composable
//fun CartIcon(cartItemCount: Int, onCartClick: () -> Unit) {
//    Box(
//        contentAlignment = Alignment.TopEnd,
//        modifier = Modifier.size(40.dp)
//    ) {
//        IconButton(onClick = onCartClick) {
//            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
//        }
//        if (cartItemCount > 0) {
//            Box(
//                modifier = Modifier
//                    .size(16.dp)
//                    .background(Color.Red, shape = CircleShape)
//                    .align(Alignment.TopEnd)
//            ) {
//                Text(
//                    text = cartItemCount.toString(),
//                    color = Color.White,
//                    fontSize = 10.sp,
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            }
//        }
//    }
//}
