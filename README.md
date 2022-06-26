# market_2022



# config file
The configuration file contains the initialized data needed for the market loading. The configuration file has two parts:
- Services configuration.
- Data configuration.

Both of the methods, are called from the isInit() method in the market only if the market initializer asks for and not automatically.

## Services Configurations
The configurations file is a txt file contains the relevand parameters for the initialization of the system external services. The sefvices can include:
- Payment service
- Supply service
- Notification service

Each of the above will appear in the file in the next path:

> ../server/config/config.txt

The file will be in the next format:
> 'service name'::'instance type'

The instances will be from the options:
- Payment service : WSEP
- Supply service : WSEP
- Notification service : Text, Notifications

If the fill will not be found, the system will be up with mock services(and wount be able to make any action with the services). 




## Data
The data nedded to be loaded in the system directly to the db. The data contains first information on users, shops, items and another objects.
When the system is being intialized, the data is being redd from of file in the system current directory under the path:
> ../server/config/data.txt

The file will be in the next format:
> 'method'::'field1'::'field2::'field3' etc..

The methods available are: 
- Register
- Login
- Logout
- Open_Shop
- Add_Item
- Appoint_Manager
- Appoint_Owner

Important! 
The methods should be in the correct order, If a method didnot appeared after all the pre calls nedded for it to succeed, it will not be executed.

If the fill will not be found, the system will be up without any initial data. 
