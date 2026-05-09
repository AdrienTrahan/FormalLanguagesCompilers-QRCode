export const prerender = true

export function GET({ request }) {
  const range = request.headers.get('Range')
  const body = ''
  
  if (range) {
    return new Response(body, {
      status: 206,
      headers: {
        'Content-Range': `bytes 0-${body.length - 1}/${body.length}`,
        'Accept-Ranges': 'bytes',
        'Content-Length': body.length
      }
    })
  }
  
  return new Response(body, {
    status: 200,
    headers: {
      'Accept-Ranges': 'bytes',
      'Content-Length': body.length
    }
  })
}