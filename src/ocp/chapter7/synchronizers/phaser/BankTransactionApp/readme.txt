Dynamic registration is one of the main reasons why Phaser is more powerful than
CountDownLatch and CyclicBarrier.
However, it's not the only advantage – Phaser also offers:

Multiple phases – you can synchronise repeatedly (phase 0,1,2…) without recreating the synchroniser.

Reusability – unlike CountDownLatch (one‑time), Phaser can be reset implicitly when the last party arrives.

Flexible arrival – methods like arrive(), arriveAndAwaitAdvance(), arriveAndDeregister() give fine‑grained control.

Party count can change over time – threads can join mid‑process (dynamic) or leave early.

When is dynamic registration really useful?
Unknown number of participants at start – e.g., reading files from a folder whose count is not fixed.

Workers that join later – e.g., a master‑worker pattern where new tasks appear.

Variable workload – some threads finish early and deregister, while others continue.

But do you always need dynamic registration?
No. If you have a fixed set of threads and a single‑phase or multi‑phase but fixed‑party workflow,
you could also use:
CyclicBarrier – fixed parties, reusability (via reset(), but it’s clumsy).

CountDownLatch – only for one‑time waiting.

So why choose Phaser even when parties are fixed?
Because Phaser gives you phase tracking for free.
Example: In your bank transaction processor, you have three phases (read, validate, write).
With CyclicBarrier, you would need three separate barriers or one barrier reset manually,
and you'd have to track which phase you're in. Phaser makes that logic clean and explicit.