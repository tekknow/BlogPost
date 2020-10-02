If you would like to see a version of these instructions that contains screenshots, please see BlogPosts.doc instead.

In order to use GitHub as your “single sign-on”, you need to create an account, if you don’t already have one.
Navigate to the Profile page and enter your Name in the Name field, as shown below:

Open the application code in your favorite IDE.  I used Eclipse.  I run the application by selecting the top most root, 
right click, select Run As > Spring Boot App.  This will build the application and run it.  You should see something 
like this in your IDE’s console:

"Started BlogPostApp in 2.327 seconds (JVM running for 3.272)"

Open a browser page and enter "localhost:8080", you should see a web page like this:

Click the “click here” link.  You should see:

When you run the Simple app for the first time, you should see something like this:

or this:

Once you click the “Authorize tekknow” button, it will never ask for it again.  From now on the application will let you right in 
without having to login, hence the term “single sign-on”.

Once you are authorized on GitHub you should see the full application web page that looks like this:

Notice the “Logged in as TekKnow”.  It extracted your name from GitHub.
When you click the “GET /posts” button, you should see something like this:

And all available posts will be in the table.

When you click the “GET /posts/1” button, you should see something like this:

When you click the “GET /posts/1/comments” button, you should see something like this:

When you click the “GET /comments?postId=1” button, you should see something like this:

When you click the “GET/posts?userId=1” button, you should see something like this:

When you enter data into the “POST /posts” form and click its button, you should see something like this:
"If this were a real post, it would have added the form fields to a repository"
Unfortunately, ran out of time.  Unable to fix "There was an unexpected error (type=Forbidden, status=403)"

When you enter data into the “PUT /posts/1” form and click its button, you should see something like this:
"If this were a real post, it would have updated the form fields associated with the postId in the repository"
Unfortunately, ran out of time.  Unable to fix "There was an unexpected error (type=Forbidden, status=403)"

When you enter data into the “PATCH /posts/1” form and click its button, you should see something like this:
"If this were a real post, it would have patched the form fields associated with the postId in the repository"
Unfortunately, ran out of time.  Unable to fix "There was an unexpected error (type=Forbidden, status=403)" 

When you enter data into the “DELETE /posts/1” form and click its button, you should see something like this:
"If this were a real post, it would have deleted the post associated with the postId from the repository"
Unfortunately, ran out of time.  Unable to fix "There was an unexpected error (type=Forbidden, status=403)" 

I suspect these errors are related to Cross-Site Request Forgery (CSRF) or permission issues.  
However, they are occuring even before the form data is sent to the server, which is puzzling to me at the moment.

As you study the code, you will notice that there are only two files, BlogPost.java and BlogPostService.java.
These are both in the "com.example" package.  Normally I would have multiple packages like "controller", "model" 
"service", "repository", "exception", etc., where there are many java files in each package.  But since this
was a relatively small project, I just left them in the main com.example package.



