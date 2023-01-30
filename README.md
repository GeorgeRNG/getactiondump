[![forthebadge](https://forthebadge.com/images/badges/contains-tasty-spaghetti-code.svg)](https://forthebadge.com)  
[![forthebadge](https://forthebadge.com/images/badges/works-on-my-machine.svg)](https://forthebadge.com)

# Get Action Dump
--- **DEPRECATED** ---
This has been optimised and can be found in [CodeClient](https://github.com/DFOnline/CodeClient)  
Used for getting updated versions of ActionDump + scraping books, this took too long to make.

## How to use
Download the mod ffs, it requires Fabric API

All files are located in the `.minecraft/getactiondump` directory.

### Getting ActionDump
I connect to df with an alternate IP (**54.39.130.89**) since it seemed to disconnect me less.  
Go into chat and run `/dumpactioninfo [-c]` (-c uses & codes)  
Wait for it to complete. There are roughly **90902 lines** and it takes me ***95 seconds*** to complete. This may change based of your internet  

Once this is complete, you will be disconnected and sent to a custom menu.  
It has three options:  
**Main Menu**: Sends you to the Main Menu.  
**Copy Data**: Copies the data exported to the actiondump (or error data if it's an error screen)  
**Copy Path**: Copies the path in which getactiondump uses.  

The database is put into `db.json`.

### Scraping books (reference book)
I detect when any book opens (by player or by DF) and then get the player's main hand item.<br/>
This is because the reference book sets the content of itself to what you chose but then resets.

The data is put into `book.txt`.
