#Project Mindful : Personal Project

## Description of the application

Mindful is a social media app similar to the likes of instagram and facebook. To join one must make an account which 
keeps track of 3 things.

- Followers
- Following
- Posts
- Saved

In the application the main ui will be the feed, which includes posts made from accounts that you follow. Posts of any 
kind including video, status or pictures are limited to a 24hr cooldown. Therefore the time window between posts 
is a minimum of 24hrs. One can save desired posts on their feed and view all their saved content in a saved tab. 
Similarly, one can also view their previous posts.

##Who will use it and why?

I chose this project idea because within all popular social medias today, there is a lot of *garbage* content that 
often fills up the feed rather quickly. These attention grabbing, quick and distracting content is what often causes the
downward spiral of content binge that makes social media addicting. Social media is often painted in a bad light because
of its distracting nature, but the reality is that it is also a platform for friends and people of interest to connect 
with others on. The 24hr time lock between posts will therefore filter and effectively remove the fast and distracting 
content and make regular posts all the more meaningful. A dedicated friend post or birthday post will mean much more 
than it does on any other media. The general target audience is for anybody with an electronic device, but this app will
be more appealing to those with a little older with less time and dont want to scroll through a ton of content to reach 
the meaningful posts. This application will have a feed that is changing at a much slower pace and therefore people can dedicate a slot
of time to check this app as one would check their email rather than scrolling endlessly throughout the day.

##User Stories

- As a user, I want to make a post
- As a user, I want to view my previous posts
- As a user, I want to view my followers
- As a user, I want to follow someone
- As a user, I want to view my following list
- As a user, I want to view my feed
- As a user, I want to all accounts to be saved
- As a user, I want to load all accounts

##Phase 4: Task 2

- Bi-directional relationship between Post and Account

##Phase 4: Task 3
- designate classes better. My GUI class is extremely large, and I could maybe split login, main and account GUI
into diff classes. These classes could extend a larger GUI class that sets the standard frame, and my ultimate GUI
  class would just call these diff classes when necessary.