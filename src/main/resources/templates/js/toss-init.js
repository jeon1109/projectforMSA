document.addEventListener('DOMContentLoaded', async () => {
    const meta = (name) => document.querySelector(`meta[name="${name}"]`)?.content ?? "";
    const clientKey = meta('toss-client-key');
    const successUrl = meta('toss-success-url') || 'http://localhost:8090/success';
    const failUrl = meta('toss-fail-url') || 'http://localhost:8090/fail';

    if (!clientKey) {
        console.error('toss clientKey가 비어 있습니다. application.yml 확인');
        return;
    }

    // 1) SDK 동적 로드
    try {
        await loadScript('https://js.tosspayments.com/v2');
    } catch (e) {
        console.error('TossPayments SDK 로드 실패:', e);
        alert('결제 스크립트를 불러오지 못했습니다. 네트워크/차단 설정을 확인하세요.');
        return;
    }

    // 2) 전역 함수 확인
    if (typeof window.TossPayments !== 'function') {
        console.error('TossPayments 전역이 없습니다. CSP/차단 문제 가능성');
        alert('결제 스크립트가 차단된 것 같습니다. 콘솔/네트워크 탭을 확인하세요.');
        return;
    }

    // 3) 위젯 초기화
    const widgets = window.TossPayments(clientKey).widgets({ customerKey: 'user-001' });

    await widgets.setAmount({ currency: 'KRW', value: 1000 });
    await widgets.renderAgreement({ selector: '#agreement' });
    await widgets.renderPaymentMethods({ selector: '#payment-widget' });

    document.getElementById('pay').addEventListener('click', async () => {
        try {
            await widgets.requestPayment({
                orderId: 'order-' + Date.now(),
                orderName: '테스트 결제',
                successUrl,
                failUrl
            });
        } catch (e) {
            console.error(e);
            alert(e?.message || '결제 요청 실패');
        }
    });
});

// 유틸: 스크립트 동적 로더
function loadScript(src) {
    return new Promise((resolve, reject) => {
        const s = document.createElement('script');
        s.src = src;
        s.async = true; // 병렬 로드
        s.onload = () => resolve();
        s.onerror = () => reject(new Error('Failed to load ' + src));
        document.head.appendChild(s);
    });
}
