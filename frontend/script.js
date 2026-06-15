const BASE_URL = 'https://codealpha-shortlink.onrender.com:8080/api/url';

    const form = document.getElementById('shorten-form');
    const input = document.getElementById('url-input');
    const hint = document.getElementById('hint');
    const submitBtn = document.getElementById('submit-btn');
    const cardsEl = document.getElementById('cards');
    const emptyState = document.getElementById('empty-state');
    const countEl = document.getElementById('count');
    const banner = document.getElementById('banner');

    const URL_PATTERN = /^https?:\/\/.+/i;

    function showBanner(message, isError) {
      banner.textContent = message;
      banner.classList.toggle('error', !!isError);
      banner.classList.add('show');
    }

    function hideBanner() {
      banner.classList.remove('show');
    }

    function validateInput() {
      const value = input.value.trim();
      const valid = URL_PATTERN.test(value);
      input.classList.toggle('invalid', value.length > 0 && !valid);
      hint.textContent = (value.length > 0 && !valid)
        ? 'That doesn\'t look like a valid URL — include http:// or https://'
        : 'Must start with http:// or https://';
      hint.classList.toggle('error', value.length > 0 && !valid);
      return valid;
    }

    input.addEventListener('input', validateInput);

    function renderCard(item) {
      const card = document.createElement('div');
      card.className = 'card';

      const shortHref = item.shortUrl || '#';
      const original = item.originalUrl || item.shortUrl || '';
      const clicks = item.clickCount ?? 0;
      const created = item.dateTime || item.createdAt || '';

      card.innerHTML = `
        
        <div class="row-short">
          <a class="short-link" href="${shortHref}" target="_blank" rel="noopener noreferrer">${shortHref}</a>
          <div class="meta">
            <span><b>${clicks}</b> clicks</span>
            <span>${created}</span>
          </div>
          <div class="row-original">${original}</div>
          <button class="copy-btn" type="button">Copy</button>
        </div>
      `;

      card.querySelector('.copy-btn').addEventListener('click', () => {
        navigator.clipboard.writeText(shortHref).then(() => {
          const btn = card.querySelector('.copy-btn');
          const original = btn.textContent;
          btn.textContent = 'Copied';
          setTimeout(() => { btn.textContent = original; }, 1200);
        });
      });

      return card;
    }

    async function loadAllUrls() {
      try {
        const res = await fetch(`${BASE_URL}/getAllUrl`);
        if (!res.ok) throw new Error('Request failed');
        const data = await res.json();

        cardsEl.innerHTML = '';

        if (!Array.isArray(data) || data.length === 0) {
          cardsEl.appendChild(emptyState);
          countEl.textContent = '0 total';
          return;
        }

        countEl.textContent = `${data.length} total`;

        // Newest first
        [...data].reverse().forEach(item => {
          cardsEl.appendChild(renderCard(item));
        });

        hideBanner();
      } catch (err) {
        showBanner('Couldn\'t reach the server — check that it\'s running on localhost:8080.', true);
      }
    }

    form.addEventListener('submit', async (e) => {
      e.preventDefault();

      if (!validateInput() || input.value.trim() === '') {
        input.classList.add('invalid');
        hint.textContent = 'Enter a URL starting with http:// or https://';
        hint.classList.add('error');
        return;
      }

      submitBtn.disabled = true;
      submitBtn.textContent = 'Generating…';

      try {
        const res = await fetch(`${BASE_URL}/short`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ originalUrl: input.value.trim() })
        });

        if (!res.ok) throw new Error('Request failed');

        input.value = '';
        validateInput();
        await loadAllUrls();
      } catch (err) {
        showBanner('Couldn\'t generate the link — check that the server is running on localhost:8080.', true);
      } finally {
        submitBtn.disabled = false;
        submitBtn.textContent = 'Generate link';
      }
    });

    // Initial load
    loadAllUrls();