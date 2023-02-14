# Hello! It's my test Restful API for employer  
Endpoints:
1)http://localhost:8080/reviews/    -GET(all),POST(body),DELETE(all)  
Example for POST:{
	"name": "KEH",
	"rating": 4.8,
	"url": "https://www.keh.com",
	"reviewsCount": 70111
}  
Example: http://localhost:8080/reviews/ 
2)http://localhost:8080/reviews/{domain} -    GET(review from site https://www.sitejabber.com/reviews/)  
Example: http://localhost:8080/reviews/keh.ru  
3)http://localhost:8080/reviews/{name} -      PUT(body), DELETE        
Example: http://localhost:8080/reviews/KEH  
Example for PUT {
	"name": "KEH",
	"rating": 4.84,
	"url": "https://www.kehffff.com",
	"reviewsCount": 70001
}  
##It was interesting task and hard sometimes. Open to critic. Have a good day
