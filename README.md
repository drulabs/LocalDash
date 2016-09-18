# LocalDash
Android local networking

This sample code contains discovery and communication between android devices in vicinity. This contains sample for Network service discovery (NSD), Wi-Fi direct and Wi-Fi service discovery.

The basic use case this code base solves is local chatting and file sharing. This is just a demonstration of data sharing (I don't really think local chat is any use), fork this and create a local tic-tac-toe or any other multiplayer game or file sharing app.

Data sharing code is kept same and a single fragment is used for displaying device list or Wi-Fi peers. The different activities are for starting relevant services and discoveries for NSD, Wi-Fi direct and Wi-Fi direct service discovery.

Wi-Fi direct service discovery is a little flaky, if you find a solution, do create a pull request.

the service type used in this codebase, is not in accordance with IANA. Review before using this code for production or commercial usage. 

When in NSD or Wi-Fi direct screen click on the floating action button to start registration or discovery respectively.

happy coding!!!

https://androiddevsimplified.wordpress.com/
