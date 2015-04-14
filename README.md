# crazy-quotes
This is a school exercise for C3L in writing robust and secure code. It was a course that was one day of lecture, three days of coding and than a presentation. TeamPostits had to do this in one day and a couple of night. 

Project description: 
YOUR TASK
Build a crazy quotes site:
The site should enable a user to:
1) Create an account, with username and password.
2) Login and authenticate the user on the site.
3) An authenticated user should be able to view, and share quotes to the community.
(Anonymous users are not supported. It is a community site. You need an account to participate.)
A quote is comprised of :
text: “Do... or do not. There is no try.” author: “Yoda”
year: 1980
createdBy: some_user
To complete the task you should use the following technology:
html, css, javascript, - frontend
https, - communication protocol, (forward from http protocol to https if required). jax-rs - backend webservices
mySql - data storage.
Note: This exercise is designed to highlight security. Write code carefully and discuss and implement security strategies at every stage of development.

SETUP: 
- Import existing Maven project (detta)
- Lägg till server - jag valde Tomcat v8.0 lägg till buildpath till vart du tankat ner Tomcat
- Högerklicka på servern och på Add/Remove och lägg till detta project. 
- I terminalen - gå in i roten av projectet och kör mvn eclipse:clean och sedan mvn eclipse:eclipse
- Starta servern och surfa in på //http://localhost:8080/crazy-quotes/quotes Du ska då få plain text "Got it!"
