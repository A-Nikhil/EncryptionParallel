#include <stdio.h>
#include<string.h>
#include<stdlib.h>
typedef unsigned int WORD;
#define w        32             /* word size in bits                 */
#define r        12             /* number of rounds                  */
#define b        16             /* number of bytes in key            */
#define c         4             /* number  words in key = ceil(8*b/w)*/
#define t        26             /* size of table S = 2*(r+1) words   */
WORD S[t];
WORD P = 0xb7e15163, Q = 0x9e3779b9;
#define ROTL(x,y) (((x)<<(y&(w-1))) | ((x)>>(w-(y&(w-1)))))
#define ROTR(x,y) (((x)>>(y&(w-1))) | ((x)<<(w-(y&(w-1)))))

void RC5_ENCRYPT(WORD *pt, WORD *ct) /* 2 WORD input pt/output ct    */
{ WORD i, A=pt[0]+S[0], B=pt[1]+S[1];
  for (i=1; i<=r; i++)
    { A = ROTL(A^B,B)+S[2*i];
      B = ROTL(B^A,A)+S[2*i+1];
    }
  ct[0] = A; ct[1] = B;
}

void RC5_DECRYPT(WORD *ct, WORD *pt) /* 2 WORD input ct/output pt    */
{ WORD i, B=ct[1], A=ct[0];
  for (i=r; i>0; i--)
    { B = ROTR(B-S[2*i+1],A)^A;
      A = ROTR(A-S[2*i],B)^B;
    }
  pt[1] = B-S[1]; pt[0] = A-S[0];
}

void RC5_SETUP(unsigned char *K) /* secret input key K[0...b-1]      */
{  WORD i, j, k, u=w/8, A, B, L[c];
   /* Initialize L, then S, then mix key into S */
   for (i=b-1,L[c-1]=0; i!=-1; i--) L[i/u] = (L[i/u]<<8)+K[i];
   for (S[0]=P,i=1; i<t; i++) S[i] = S[i-1]+Q;
   for (A=B=i=j=k=0; k<3*t; k++,i=(i+1)%t,j=(j+1)%c)   /* 3*t > 3*c */
     { A = S[i] = ROTL(S[i]+(A+B),3);
       B = L[j] = ROTL(L[j]+(A+B),(A+B));
     }
}

void main()
{
    WORD i,j,pt1[2],pt2[2], ct[2]={0,0};
    unsigned char key[b];
    if (sizeof(WORD)!=4)
        printf("RC5 error: WORD has %d bytes.\n",sizeof(WORD));
    printf("RC5-32/12/16 encryption and decryption:\n");
    for(i=1;i<=3;i++)
    {
        pt1[0]="Silicon Valley is facing these days. Its amoral view of capital is increasingly clashing with the reality that it matters a whole heck of a lot where that capital comes from. And it is about time that founders and investors take responsibility for cleaning up a capital base that has become more and more squalid over time. Why can’t capital just be immoral? Well, Epstein’s web of donations provided him with a philanthropic sheen that eased access to the highest echelons of society while he committed his crimes. Saudi Arabia is the largest investor in Silicon Valley not only because it drives a return and diversifies its oil-dependent economy, but also because it can Valley-wash the horrific rights abuses and atrocities it commits against all of its citizens, including women, LGBT people, and immigrants. (But hey, women can drive now, just in time for autonomous vehicles.) This amoral versus moral view of capital is just the classic debate in philosophy between utilitarianism versus deontological duties, but Silicon Valley has almost exclusively chosen the former rather than the latter. My bank asks me more questions about my $50 deposits than many founders ask about where that $500 million check comes from. That’s perhaps understandable in context. Founders — as with non-profit leaders — fundraise around-the-clock. When a check finally arrives, they don’t bother to ask a bunch of due diligence questions. They just want that money to hit the bank and get back to building what they were intending to the entire time. It’s a mode of operating that continues to the present day. I was chatting with a founder this week, and during demo day last week, he got an emailed check for $50,000 from an investor in the audience. It was amazing, he said with exclamation points to me, and it sounded like he just added the check to the pile he had accumulated. Who is this person? Do we know where his capital comes from? Is there going to be some scandal that shocks the startup in a couple of years? Yet the excitement was palpable — the round was closed, and it was the easiest $50,000 ever fundraised. Those diligence questions probably didn’t need to be asked a decade or two in the Valley, back when a few dozen firms mostly raised from blue-chip university and non-profit endowments as well as state pension funds. Today though, there are all kinds of sources of capital, with little clarity about where the capital is coming from. Take, for instance, Carlos Ghosn, who once headed Nissan Motors and is currently on trial in Japan for a variety of financial crimes. He has been accused of embezzling millions of dollars for a VC fund run by his son by running a kickback scheme through a Nissan distributor in Lebanon. As the Wall Street Journal reported a little more than a week ago: In March 2015, the Ghosns set up in Delaware an investment vehicle called Shogun Investments, which Mr. Ghosn described as a fund that would invest in Silicon Valley startups. Mr. Ghosn was majority owner while his son, Anthony, held a stake, according to people famil" ;pt1[1]="iar with the matter. The younger Mr. Ghosn, who was about to graduate from Stanford University, was working at the time as chief of staff for Silicon Valley venture capitalist Joe Lonsdale, providing the elder Mr. Ghosn a close-up view of the tech investment world. The lofty returns had stunned him, according to one of the people. That fund would go on to fund some of the most well-known unicorns in the world: “Following our phone conversation, I ordered a transfer of $3 million,” Carlos Ghosn wrote in a December 2017 email to his son, who was 22 years old at the time. Of that amount, $2 million was for an investment in Grab, a Southeast Asian competitor to Uber Technologies Inc., Mr. Ghosn wrote, adding that he was sending “$1 million for the company of your friend that you think will do very well.” It wasn’t clear which company Mr. Ghosn was referring to. I would love a world in which founders asked all the right due diligence questions. I would love for them to inquire about limited partners, about how wealth was created, and how it has been invested. But I am also aware that in what can be a desperate search for funds, those questions may well never get asked in the first place. If you want to stop the capital laundering taking place every day in the Valley, you have to create active, real-time antidotes. That means stopping it at every point of contact, every single opportunity where it can infect the ecosystem. And so, we need better systems as a community and as an ecosystem to cleanse ourselves of this dirty money. We need “know-your-capital” processes that are standardized, robust, and accurate so that every check can be verified before it hits the bank. We need tools to verify that a startup or non-profit has actually followed those KYC processes, so that employees don’t suddenly show up at work and realize they are making money for a bunch of murderers. It’s “trust but verify.” Systematization and process are key to execution, but that doesn’t disclaim the responsibility for the Valley’s leaders to take a moral stance here. Utilitarianism only takes you so far — it does matter that you take capital from a bad actor. Negroponte is wrong to say that he would still take Epstein’s money, regardless of what that capital might have funded at the MIT Media Lab. Taking responsibility for your capital is part of being a leader of an organization today. Hopefully, the next generation of founders will take a look at Epstein, and Khashoggi, and China, and Ghosn, and the Sacklers, and a whole host of other case studies and learn from them and change their fundraising practices. A moral view on capital isn’t a cost of doing business — it’s simply the right thing to do.";
        for(j=0;j<b;j++)
            key[j]=rand()%10;
        RC5_SETUP(key);
        RC5_ENCRYPT(pt1,ct);
        RC5_DECRYPT(ct,pt2);
        printf("\n%d. key = ",i);
        for(j=0;j<b;j++)
            printf("%.2X ",key[j]);
        printf("\nEncryption\n plaintext %s %s ---> ciphertext %.8X %.8X \n",pt1[0],pt1[1],ct[0],ct[1]);
        printf("\nDecryption\n ciphertext %.8X %.8X ---> plaintext %s %s \n",ct[0],ct[1],pt2[0],pt2[1]);
        if(pt1[0]!=pt2[0] || pt1[1]!=pt2[1])
            printf("Decryption Error!");
    }
}
