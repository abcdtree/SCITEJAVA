# SCITEJAVA
SCITE Research In JAVA
There is an example in MyScite.java for how to use MCMC to find the best score and the tree.

There are three modes for initalizing MCMC class.
First, new MCMC(dMatrix, vafMatrix, alpha, beta, true)
This is an initialzation to start the mcmc with a binary builded initial tree.
Second, new MCMC(dMatrix, vafMatrix, alpha, beta, false)
This is an initialzation to start the mcmc with a linear builded initial tree.
Third, new MCMC(sciteTree, dMatrix, vafMatrix, alpha, beta)
This mcmc is initialize with a specific tree structure which must build with all the mutations in dMatrix and vafMatirx.

now, I did not add VafMatrix into the score system of MCMC, but it is very easy to set up.
Read the startMCMC method in MCMC class and override another one change the getScore() to getScore()*getVafScore(),
Then it will calculate with vafMatrix.

I already did some experiment and this code will result different best score every time.
