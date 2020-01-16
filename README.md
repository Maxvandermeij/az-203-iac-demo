### Basic Infrastructure as Code example

This project demonstrates a quick way of doing your infrastructure as code. 
The project is set up to be testable, as part of the presentation I prepared on this topic.


#### Why this project?

This project isn't intended to be an example as to how to properly do infrastructure as code.
It's meant to raise awareness about testing any code, including infrastructure as code.
From my work as software engineer I've come to strongly believe in the benefit of testing code.

Java is definitely not the (only) answer to testing your infrastructure. 
I've created this in Java as I am most familiar with the language.

#### Honourable mentions

There are a few honourable mentions when it comes to testing infrastructure as code.
* Terraform 
    * Static analysis: terraform validate
    * Dry run: terraform plan
* Kubernetes
    * Static analysis: kubectl apply -f <file> --dry-run --validate=true
    * Static analysis: kube-score https://github.com/zegl/kube-score
    * Dry run: kubectl apply -f <file> --server-dry-run
    
When it comes "unit" testing, this is where Infrastructure as code is troublesome.  
My definition is that you'd be integration testing one singular unit of infrastructure.
For example, we'd deploy a virtual machine and test if it is according to the specification defined.
For Azure, there's an option to integrate this into your pipelines using Azure Stack. 
While it offers many options, I am considering Azure Stack out of scope for this.

* Terraform
    * Terratest https://github.com/gruntwork-io/terratest
* Cloud infrastructure
    * Inspec https://www.inspec.io/
    * Manual test suites written in house
    
#### Setup the project

The project is pretty straight forward. 
The only thing you'd need to do is fill in the AzureMetaData class with the correct information.
The information needed is found on the Azure Portal, after registering and authorizing an application in your Azure Active Directory.
Make sure that the application has access to the required resources, namely the Azure ARM API.
